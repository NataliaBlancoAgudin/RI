package uo.ri.cws.application.service.professionalGroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalGroup.crud.ProfessionalGroupDtoAssembler;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProfessionalGroup implements Command<Void> {

    private ProfessionalGroupDto dto;

    /// TDGs que necesito
    ProfessionalGroupGateway pg = Factories.persistence.forProfessionalGroup();

    public UpdateProfessionalGroup(ProfessionalGroupDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isTrue(dto.trienniumPayment > 0);
	ArgumentChecks.isTrue(dto.productivityRate > 0);

	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	// Comprobaciones
	Optional<ProfessionalGroupRecord> pr = pg.findByName(dto.name);
	BusinessChecks.exists(pr, "The professional group does not exist");
	BusinessChecks.hasVersion(dto.version, pr.get().version);

	pg.update(ProfessionalGroupDtoAssembler.toRecord(dto));

	return null;
    }

}
