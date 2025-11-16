package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProfessionalGroup implements Command<Void> {

    private ProfessionalGroupDto dto;

    private ProfessionalGroupRepository pgrepo = Factories.repository.forProfessionalGroup();

    public UpdateProfessionalGroup(ProfessionalGroupDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isTrue(dto.trienniumPayment >= 0);
	ArgumentChecks.isTrue(dto.productivityRate >= 0);
	ArgumentChecks.isNotBlank(dto.name);

	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroup> opg = pgrepo.findByName(dto.name);
	BusinessChecks.exists(opg, "The professional group does not exists");

	ProfessionalGroup pg = opg.get();
	BusinessChecks.hasVersion(dto.version, pg.getVersion());

	pg.setName(dto.name);
	pg.setProductivityRate(dto.productivityRate);
	pg.setTrienniumPayment(dto.trienniumPayment);

	return null;
    }

}
