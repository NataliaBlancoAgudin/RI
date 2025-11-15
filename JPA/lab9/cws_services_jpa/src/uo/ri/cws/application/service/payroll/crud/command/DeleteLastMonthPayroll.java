package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPayroll implements Command<Integer> {

    private PayrollRepository prepo = Factories.repository.forPayroll();

    @Override
    public Integer execute() throws BusinessException {
	List<Payroll> lista = prepo.findLastMonthPayrolls();

	for (Payroll p : lista) {
	    prepo.remove(p);
	}

	return lista.size();
    }

}
