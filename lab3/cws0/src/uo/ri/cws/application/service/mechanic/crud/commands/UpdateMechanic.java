package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

/// este implementa el command y devuelve el Void (por
public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;

    /// TDGs que necesito (en este caso solo necesito los mecanicos)
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public UpdateMechanic(MechanicDto dto) {
	/// Comprobaciones
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isNotBlank(dto.surname);
	ArgumentChecks.isNotNull(dto.nif);
	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<MechanicRecord> om = mg.findById(dto.id);
	/// COMPROBAMOS: la interfaz de servicio me lo dice
	BusinessChecks.exists(om, "The mechanic does not exist");
	/// ESTO HAY QUE HACERLO CON TODOS LOS UPDATE
	BusinessChecks.hasVersion(dto.version, om.get().version);

	mg.update(MechanicDtoAssembler.toRecord(dto));

	return null;
    }

}
