package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.crud.command.DeleteLastMonthPayroll;
import uo.ri.cws.application.service.payroll.crud.command.DeleteLastMonthPayrollOfMechanic;
import uo.ri.cws.application.service.payroll.crud.command.ListPayrollsOfMechanic;
import uo.ri.cws.application.service.payroll.crud.command.ListPayrollsOfProfGroup;
import uo.ri.cws.application.service.payroll.crud.command.ShowPayroll;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class PayrollServiceImpl implements PayrollService {

    private CommandExecutor executor = Factories.executor.forExecutor();

    @Override
    public List<PayrollDto> generateForPreviousMonth()
	throws BusinessException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<PayrollDto> generateForPreviousMonthOf(LocalDate present)
	throws BusinessException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteLastGeneratedOfMechanicId(String mechanicId)
	throws BusinessException {
	executor.execute(new DeleteLastMonthPayrollOfMechanic(mechanicId));

    }

    @Override
    public int deleteLastGenerated() throws BusinessException {
	return executor.execute(new DeleteLastMonthPayroll());
    }

    @Override
    public Optional<PayrollDto> findById(String id) throws BusinessException {
	return executor.execute(new ShowPayroll(id));
    }

    @Override
    public List<PayrollSummaryDto> findAllSummarized()
	throws BusinessException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<PayrollSummaryDto> findSummarizedByMechanicId(String id)
	throws BusinessException {
	return executor.execute(new ListPayrollsOfMechanic(id));
    }

    @Override
    public List<PayrollSummaryDto> findSummarizedByProfessionalGroupName(
	String name) throws BusinessException {
	return executor.execute(new ListPayrollsOfProfGroup(name));
    }

}
