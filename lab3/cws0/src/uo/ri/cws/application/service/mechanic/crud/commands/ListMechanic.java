package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class ListMechanic implements Command<Optional<MechanicDto>> {

    private String nif;

    private MechanicGateway mg = Factories.persistence.forMechanic();

    public ListMechanic(String nif) {
	ArgumentChecks.isNotNull(nif);
	ArgumentChecks.isNotBlank(nif);
	this.nif = nif;
    }

    @Override
    public Optional<MechanicDto> execute() {
	return MechanicDtoAssembler.toDtoOptional(mg.findByNif(nif));
    }

}
