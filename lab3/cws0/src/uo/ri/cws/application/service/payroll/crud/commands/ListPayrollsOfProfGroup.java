package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class ListPayrollsOfProfGroup
    implements Command<List<PayrollSummaryDto>> {

    private String groupName;

    // gateway que necesito
    PayrollGateway pg = Factories.persistence.forPayroll();
    ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();

    public ListPayrollsOfProfGroup(String groupName) {
	ArgumentChecks.isNotNull(groupName);
	ArgumentChecks.isNotBlank(groupName);

	this.groupName = groupName;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
	Optional<ProfessionalGroupRecord> pr = pgg.findByName(groupName);
	BusinessChecks.exists(pr, "The professional group does not exist");

	List<PayrollRecord> payR = pg
	    .findPayrollsByProfessionalGroupName(groupName);

	return PayrollDtoAssembler.toSummaryDtoList(payR);
    }

}
