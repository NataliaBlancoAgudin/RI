package uo.ri.cws.application.service.payroll.crud.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class GeneratePayrollsAtDate implements Command<List<PayrollDto>> {

    private LocalDate date;

    private PayrollRepository prepo = Factories.repository.forPayroll();
    private ContractRepository crepo = Factories.repository.forContract();

    public GeneratePayrollsAtDate(LocalDate date) {
	// validamos
	ArgumentChecks.isNotNull(date);

	this.date = date;
    }

    @Override
    public List<PayrollDto> execute() throws BusinessException {
	List<Payroll> payrolls = new ArrayList<Payroll>();

	// comprobamos primero que no haya ninguna payroll ya este mes
	LocalDate lastDay = date.minusMonths(1)
				.withDayOfMonth(
				    date.minusMonths(1).lengthOfMonth());

	List<Payroll> lista = prepo.findByDate(lastDay);
	if (!lista.isEmpty()) {
	    return DtoAssembler.toDtoList(payrolls);
	}

	List<Contract> contracts = crepo.findAllInForceThisMonth(date);

	for (Contract c : contracts) {
	    Payroll p = new Payroll(c, lastDay);
	    prepo.add(p);
	    payrolls.add(p);
	}

	return DtoAssembler.toDtoList(payrolls);
    }

}
