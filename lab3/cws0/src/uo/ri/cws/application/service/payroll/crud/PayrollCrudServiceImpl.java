package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.crud.commands.DeleteLastMonthPayroll;
import uo.ri.cws.application.service.payroll.crud.commands.DeleteLastMonthPayrollOfMechanic;
import uo.ri.cws.application.service.payroll.crud.commands.ListAllPayrollsSummarized;
import uo.ri.cws.application.service.payroll.crud.commands.GeneratePayrollsAtDate;
import uo.ri.cws.application.service.payroll.crud.commands.ListPayrollsOfMechanic;
import uo.ri.cws.application.service.payroll.crud.commands.ListPayrollsOfProfGroup;
import uo.ri.cws.application.service.payroll.crud.commands.ShowPayroll;
import uo.ri.util.exception.BusinessException;

public class PayrollCrudServiceImpl implements PayrollService {

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public List<PayrollDto> generateForPreviousMonth()
	throws BusinessException {
	/// Este metodo delegara su funcion en el metodo siguiente para no
	/// duplicar el codigo
	return generateForPreviousMonthOf(LocalDate.now());
    }

    @Override
    public List<PayrollDto> generateForPreviousMonthOf(LocalDate present)
	throws BusinessException {
	return executor.execute(new GeneratePayrollsAtDate(present));
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
	return executor.execute(new ListAllPayrollsSummarized());
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
