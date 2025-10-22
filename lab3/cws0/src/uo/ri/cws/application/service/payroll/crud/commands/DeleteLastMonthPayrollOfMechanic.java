package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPayrollOfMechanic implements Command<Void> {

    private String mechanicId;

    // Gateway que necesito
    PayrollGateway pg = Factories.persistence.forPayroll();
    MechanicGateway mg = Factories.persistence.forMechanic();

    public DeleteLastMonthPayrollOfMechanic(String mechanicId) {
	ArgumentChecks.isNotBlank(mechanicId);

	this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
	// Comprobamos que exista el mecanico
	Optional<MechanicRecord> m = mg.findById(mechanicId);
	BusinessChecks.exists(m, "The mechanic does not exists");

	// calculamos el ultimo dia del mes anterior en el que estamos
	LocalDate lastMonth = LocalDate.now().minusMonths(1);

	// Recuperamos las payrolls del empleado dado
	List<PayrollRecord> pl = pg.findPayrollsOfMechanicByDate(mechanicId,
	    lastMonth);

	for (PayrollRecord pr : pl) {
	    pg.remove(pr.id);
	}

	return null;
    }

}
