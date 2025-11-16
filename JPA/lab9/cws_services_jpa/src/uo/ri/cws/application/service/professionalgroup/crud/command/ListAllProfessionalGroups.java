package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.exception.BusinessException;

public class ListAllProfessionalGroups
    implements Command<List<ProfessionalGroupDto>> {

    private ProfessionalGroupRepository pgrepo = Factories.repository.forProfessionalGroup();

    @Override
    public List<ProfessionalGroupDto> execute() throws BusinessException {
	List<ProfessionalGroup> lista = pgrepo.findAll();
	return DtoAssembler.toProfessionalGroupDtoList(lista);
    }

}
