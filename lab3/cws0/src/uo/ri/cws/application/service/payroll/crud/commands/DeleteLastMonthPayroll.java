package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;

public class DeleteLastMonthPayroll implements Command<Integer> {

    // Factorias que necesito
    PayrollGateway pg = Factories.persistence.forPayroll();

    @Override
    public Integer execute() {
	// calculamos el ultimo dia del mes anterior en el que estamos
	LocalDate lastMonth = LocalDate.now().minusMonths(1);

	// recogemos las payroll de dicho mes
	List<PayrollRecord> lpr = pg.findPayrollsByDate(lastMonth);

	// las eliminamos
	for (PayrollRecord p : lpr) {
	    pg.remove(p.id);
	}

	return lpr.size();

    }

}
