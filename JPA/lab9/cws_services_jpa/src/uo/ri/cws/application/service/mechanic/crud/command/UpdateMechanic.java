package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;

    private MechanicRepository repo = Factories.repository.forMechanic();

    public UpdateMechanic(MechanicDto dto) {
	// validaciones del dto pasado como parametro
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.nif);
	ArgumentChecks.isNotBlank(dto.surname);
	ArgumentChecks.isNotBlank(dto.name);

	this.dto = dto;

    }

    @Override
    public Void execute() throws BusinessException {
	Optional<Mechanic> om = repo.findById(dto.id);
	BusinessChecks.exists(om);

	// esta persistido, por lo que yo modifico los objetos y ya estan
	// ya esta actualizado (lo hace el mapeador)
	Mechanic m = om.get();
	// COMPARAMOS VERSIONESSS!!!!
	BusinessChecks.hasVersion(dto.version, m.getVersion());

	m.setName(dto.name);
	m.setSurname(dto.surname);

	return null;
    }

}
