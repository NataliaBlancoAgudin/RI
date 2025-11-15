package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto dto;

    private MechanicRepository repo = Factories.repository.forMechanic();

    public AddMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.nif);
	ArgumentChecks.isNotBlank(dto.surname);
	ArgumentChecks.isNotBlank(dto.name);

	this.dto = dto;

    }

    @Override
    public MechanicDto execute() throws BusinessException {
	Optional<Mechanic> om = repo.findByNif(dto.nif);
	BusinessChecks.doesNotExist(om, "The mechanic already exists");

	// Creamos el mecanico: OJO! Tiene que ser un objeto mecanico
	Mechanic m = new Mechanic(dto.nif, dto.surname, dto.name);
	repo.add(m);

	return DtoAssembler.toDto(m);
    }

}
