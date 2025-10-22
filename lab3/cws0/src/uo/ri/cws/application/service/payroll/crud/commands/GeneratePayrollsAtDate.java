package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.PayrollDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class GeneratePayrollsAtDate implements Command<List<PayrollDto>> {

    private LocalDate date;

    // TDGs que necesito
    PayrollGateway pg = Factories.persistence.forPayroll();
    ContractGateway cg = Factories.persistence.forContract();
    ProfessionalGroupGateway fg = Factories.persistence.forProfessionalGroup();
    WorkOrderGateway wg = Factories.persistence.forWorkOrder();

    public GeneratePayrollsAtDate(LocalDate date) {
	ArgumentChecks.isNotNull(date);

	this.date = date;
    }

    @Override
    public List<PayrollDto> execute() {
	// 1. Calcular el mes anterior
	YearMonth previousMonth = YearMonth.from(date.minusMonths(1));
	// 1.1. Calculamos el ultimo dia del mes anterior
	LocalDate lastDayOfPreviousMonth = previousMonth.atEndOfMonth();

	// 2. Comrpobar si ya existen nominas de ese mes
	List<PayrollRecord> lp = pg.findPayrollsByDate(lastDayOfPreviousMonth);
	if (!lp.isEmpty()) {
	    // Si ya tiene nominas ese mes, no se genera nignuna nueva
	    return new ArrayList<>();
	}

	// 3. Busca contratos en vigor o terminados en ese mes
	List<String> contractIds = cg
	    .findContractsInMonth(lastDayOfPreviousMonth);
	System.out.println("contratos " + contractIds.size());

	List<PayrollDto> generatedPayrolls = new ArrayList<>();

	// 4. Para cada contrato creamos la nomina
	for (String contractId : contractIds) {
	    PayrollDto dto = generatePayrollForContract(contractId,
		lastDayOfPreviousMonth);
	    pg.add(PayrollDtoAssembler.toRecord(dto));
	    generatedPayrolls.add(dto);
	}

	return generatedPayrolls;
    }

    private PayrollDto generatePayrollForContract(String contractId,
	LocalDate date) {
	PayrollDto dto = new PayrollDto();

	// Recuperamos el contrato y el professionalGroup
	ContractRecord cr = cg.findById(contractId).get();
	ProfessionalGroupRecord pr = fg.findById(cr.professionalgroup_id).get();

	// Obtenemos los valores que nos interesan
	// Calcular salario base mensual
	double baseSalary = cr.annualbasesalary / 14.0;
	// Calcular extra
	double extraSalary = 0.0;
	if (date.getMonth() == Month.JUNE
	    || date.getMonth() == Month.DECEMBER) {
	    extraSalary = baseSalary;
	}

	// Calcular productividad
	double totalWorkordersAmount = wg
	    .findTotalInvoicedForMechanicInMonth(cr.mechanic_id, date);
	double productivityEarning = totalWorkordersAmount
	    * pr.productivityRate;

	/// Calcular trienios
	long years = ChronoUnit.YEARS.between(cr.startdate.toLocalDate(), date);
	double trienniumEarning = (years / 3) * pr.trienniumPayment;

	// Calclar totales brutos
	double grossSalary = baseSalary + extraSalary + trienniumEarning
	    + productivityEarning;

	double taxDeduction = grossSalary * cr.taxrate;
	double nicDeduction = (cr.annualbasesalary * 0.05) / 12.0;
	double totalDeductions = taxDeduction + nicDeduction;

	double netSalary = grossSalary - totalDeductions;

	// creamos el dto
	dto.id = UUID.randomUUID().toString();
	dto.version = 1;

	dto.contractId = contractId;
	dto.date = date;

	dto.baseSalary = baseSalary;
	dto.extraSalary = extraSalary;
	dto.productivityEarning = productivityEarning;
	dto.trienniumEarning = trienniumEarning;

	dto.taxDeduction = taxDeduction;
	dto.nicDeduction = nicDeduction;

	dto.netSalary = netSalary;
	dto.grossSalary = grossSalary;
	dto.totalDeductions = totalDeductions;
	return dto;
    }

}
