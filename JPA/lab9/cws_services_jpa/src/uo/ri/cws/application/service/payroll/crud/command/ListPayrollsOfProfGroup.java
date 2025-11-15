package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListPayrollsOfProfGroup
    implements Command<List<PayrollSummaryDto>> {

    private String groupName;

    private PayrollRepository prepo = Factories.repository.forPayroll();

    public ListPayrollsOfProfGroup(String name) {
	ArgumentChecks.isNotBlank(name);

	this.groupName = name;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
	List<Payroll> lista = prepo.findByProfessionalGroupName(groupName);

	return DtoAssembler.toSummaryDtoList(lista);
    }

}
