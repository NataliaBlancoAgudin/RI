package uo.ri.cws.application.ui.manager.payroll.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.menu.Action;

public class ListAllPayrollsSummarizedAction implements Action {

    @Override
    public void execute() throws Exception {

	PayrollService ps = Factories.service.forPayrollService();

	List<PayrollSummaryDto> payrolls = ps.findAllSummarized();

	for (PayrollSummaryDto dto : payrolls) {
	    Printer.printPayrollSummary(dto);
	}

    }

}
