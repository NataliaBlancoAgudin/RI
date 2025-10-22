package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollDtoAssembler;

public class FindAllSummarized implements Command<List<PayrollSummaryDto>> {

    // gateway que necesito
    PayrollGateway pg = Factories.persistence.forPayroll();

    @Override
    public List<PayrollSummaryDto> execute() {
	List<PayrollRecord> plr = pg.findAll();
	return PayrollDtoAssembler.toSummaryDtoList(plr);
    }

}
