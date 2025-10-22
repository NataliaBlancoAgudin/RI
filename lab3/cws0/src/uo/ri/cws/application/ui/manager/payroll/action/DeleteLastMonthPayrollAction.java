package uo.ri.cws.application.ui.manager.payroll.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteLastMonthPayrollAction implements Action {

    @Override
    public void execute() throws BusinessException {

	PayrollService ps = Factories.service.forPayrollService();
	ps.deleteLastGenerated();

	Console.println("Last month's payrolls deleted");
    }
}