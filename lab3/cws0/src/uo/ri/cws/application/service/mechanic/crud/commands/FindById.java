package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class FindById implements Command<Optional<MechanicDto>> {

    private String id;

    /// TDGs que necesito (en este caso solo necesito los mecanicos)
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public FindById(String id) {
	ArgumentChecks.isNotNull(id);
	ArgumentChecks.isNotBlank(id);

	this.id = id;
    }

    @Override
    public Optional<MechanicDto> execute() {
	return MechanicDtoAssembler.toDtoOptional(mg.findById(id));
    }
}
