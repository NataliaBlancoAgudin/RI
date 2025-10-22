package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class ListPayrollsOfMechanic
    implements Command<List<PayrollSummaryDto>> {

    private String mechanicId;

    // Gateway que necesito
    MechanicGateway mg = Factories.persistence.forMechanic();
    PayrollGateway pg = Factories.persistence.forPayroll();

    public ListPayrollsOfMechanic(String mechanicId) {
	ArgumentChecks.isNotNull(mechanicId);
	ArgumentChecks.isNotBlank(mechanicId);

	this.mechanicId = mechanicId;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
	// Comprobamos que exista el mecanico
	Optional<MechanicRecord> m = mg.findById(mechanicId);
	BusinessChecks.exists(m, "The mechanic does not exists");

	List<PayrollRecord> lpr = pg.findPayrollsByMechanicId(mechanicId);
	return PayrollDtoAssembler.toSummaryDtoList(lpr);
    }
}
