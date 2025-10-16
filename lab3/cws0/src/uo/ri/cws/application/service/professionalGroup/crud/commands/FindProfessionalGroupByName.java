package uo.ri.cws.application.service.professionalGroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalGroup.crud.ProfessionalGroupDtoAssembler;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.assertion.ArgumentChecks;

public class FindProfessionalGroupByName
    implements Command<Optional<ProfessionalGroupDto>> {

    private String name;

    /// TDGs que necesito
    private ProfessionalGroupGateway fgw = Factories.persistence
	.forProfessionalGroup();

    public FindProfessionalGroupByName(String name) {
	ArgumentChecks.isNotNull(name);
	ArgumentChecks.isNotBlank(name);

	this.name = name;
    }

    public Optional<ProfessionalGroupDto> execute() {
	return ProfessionalGroupDtoAssembler
	    .toDtoOptional(fgw.findByName(name));
    }

}
