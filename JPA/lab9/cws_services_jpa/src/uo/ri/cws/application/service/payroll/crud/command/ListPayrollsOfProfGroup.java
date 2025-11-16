package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class ListPayrollsOfProfGroup
    implements Command<List<PayrollSummaryDto>> {

    private String groupName;

    private PayrollRepository prepo = Factories.repository.forPayroll();
    private ProfessionalGroupRepository pgrepo = Factories.repository.forProfessionalGroup();

    public ListPayrollsOfProfGroup(String name) {
	ArgumentChecks.isNotBlank(name);

	this.groupName = name;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
	// buscamos primero el grupo profesional
	Optional<ProfessionalGroup> opg = pgrepo.findByName(groupName);
	BusinessChecks.exists(opg, "The professional group does not exists");

	List<Payroll> lista = prepo.findByProfessionalGroupName(groupName);

	return DtoAssembler.toSummaryDtoList(lista);
    }

}
