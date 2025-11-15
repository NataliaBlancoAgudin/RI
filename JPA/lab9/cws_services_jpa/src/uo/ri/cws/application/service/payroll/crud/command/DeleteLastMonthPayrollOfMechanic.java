package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPayrollOfMechanic implements Command<Void> {

    private String mechanicId;

    private PayrollRepository prepo = Factories.repository.forPayroll();
    private MechanicRepository mrepo = Factories.repository.forMechanic();

    public DeleteLastMonthPayrollOfMechanic(String mechanicId) {
	// validaciones
	ArgumentChecks.isNotBlank(mechanicId);

	this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
	// comprobamos que el mecanico exista antes
	Optional<Mechanic> om = mrepo.findById(mechanicId);
	BusinessChecks.exists(om, "The mechanic does not exists");

	// buscamos las payrolls del mecanico y las eliminamos
	List<Payroll> lista = prepo.findByMechanicIdLastMonth(mechanicId);

	for (Payroll p : lista) {
	    prepo.remove(p);
	}

	return null;
    }

}
