package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ShowPayroll implements Command<Optional<PayrollDto>> {

    private String id;

    private PayrollRepository prepo = Factories.repository.forPayroll();

    public ShowPayroll(String id) {
	ArgumentChecks.isNotBlank(id);

	this.id = id;
    }

    @Override
    public Optional<PayrollDto> execute() throws BusinessException {
	Optional<Payroll> op = prepo.findById(id);
	return op.map(m -> DtoAssembler.toDto(m));
    }

}
