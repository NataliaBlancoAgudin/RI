package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.util.assertion.ArgumentChecks;

public class GenerateForPreviousMonthOf implements Command<List<PayrollDto>> {

    private LocalDate date;

    // TDGs que necesito
    PayrollGateway pg = Factories.persistence.forPayroll();
    ContractGateway cg = Factories.persistence.forContract();

    public GenerateForPreviousMonthOf(LocalDate date) {
	ArgumentChecks.isNotNull(date);

	this.date = date;
    }

    @Override
    public List<PayrollDto> execute() {
	// 1. Calcular el mes anterior
	YearMonth previousMonth = YearMonth.from(date.minusMonths(1)); // Calculamos
								       // el mes
								       // anterior
	LocalDate lastDayOfPreviousMonth = previousMonth.atEndOfMonth();

	// 2. Comrpobar si ya existen nominas de ese mes
	List<PayrollRecord> lp = pg.findPayrollsByDate(lastDayOfPreviousMonth);
	if (!lp.isEmpty()) {
	    // Si ya tiene nominas ese mes, no se genera nignuna nueva
	    return new ArrayList<>();
	}

	// 3. Busca contratos en vigor o temrinados en ese mes
	List<String> contractIds = cg
	    .findContractsInMonth(lastDayOfPreviousMonth);

	return null;
    }

}
