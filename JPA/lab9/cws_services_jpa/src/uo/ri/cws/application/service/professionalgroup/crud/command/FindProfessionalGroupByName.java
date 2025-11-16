package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindProfessionalGroupByName
    implements Command<Optional<ProfessionalGroupDto>> {

    private String name;

    private ProfessionalGroupRepository pgrepo = Factories.repository.forProfessionalGroup();

    public FindProfessionalGroupByName(String name) {
	ArgumentChecks.isNotBlank(name);

	this.name = name;
    }

    @Override
    public Optional<ProfessionalGroupDto> execute() throws BusinessException {
	Optional<ProfessionalGroup> opg = pgrepo.findByName(name);
	return opg.map(p -> DtoAssembler.toDto(p));
    }

}
