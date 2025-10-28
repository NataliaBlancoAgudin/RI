package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto dto;

    /// TDGs que necesito
    MechanicGateway mg = Factories.persistence.forMechanic();

    public AddMechanic(MechanicDto dto) {
	/// Comprobaciones
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.nif);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isNotBlank(dto.surname);

	/// Esto es mejor dejarlo en esta capa y no en la de Interacción con el
	/// usuario
	dto.id = UUID.randomUUID().toString();
	dto.version = 1;

	this.dto = dto;
    }

    @Override
    public MechanicDto execute() throws BusinessException {
	Optional<MechanicRecord> om = mg.findByNif(dto.nif);

	BusinessChecks.doesNotExist(om,
	    "A mechanic with nif " + dto.nif + " already exists");

	mg.add(MechanicDtoAssembler.toRecord(dto));

	return dto;

    }

}
