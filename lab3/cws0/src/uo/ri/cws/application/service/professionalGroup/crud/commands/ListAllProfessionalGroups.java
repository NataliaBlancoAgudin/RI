package uo.ri.cws.application.service.professionalGroup.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalGroup.crud.ProfessionalGroupDtoAssembler;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;

public class ListAllProfessionalGroups
    implements Command<List<ProfessionalGroupDto>> {

    private ProfessionalGroupGateway pg = Factories.persistence
	.forProfessionalGroup();

    @Override
    public List<ProfessionalGroupDto> execute() {
	return ProfessionalGroupDtoAssembler.toDtoList(pg.findAll());
    }

}
