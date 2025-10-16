package uo.ri.cws.application.service.professionalGroup.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalGroup.crud.ProfessionalGroupDtoAssembler;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProfessionalGroup implements Command<ProfessionalGroupDto> {

    ProfessionalGroupDto dto;

    /// TDGs que necesito
    ProfessionalGroupGateway pgw = Factories.persistence.forProfessionalGroup();

    public AddProfessionalGroup(ProfessionalGroupDto dto) {
	/// Comprobaciones
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isTrue(dto.trienniumPayment > 0);
	ArgumentChecks.isTrue(dto.productivityRate > 0);

	/// Esto es mejor dejarlo en esta capa y no en la de Interacción con el
	/// usuario
	dto.id = UUID.randomUUID().toString();
	dto.version = 1;

	this.dto = dto;
    }

    public ProfessionalGroupDto execute() throws BusinessException {
	Optional<ProfessionalGroupRecord> op = pgw.findByName(dto.name);

	BusinessChecks.doesNotExist(op,
	    "Professionalgroup with name " + dto.name + "already exists");

	pgw.add(ProfessionalGroupDtoAssembler.toRecord(dto));

	return dto;
    }

}
