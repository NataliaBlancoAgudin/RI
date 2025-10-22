package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;

public class PayrollDtoAssembler {

    /**
     * Pasa un PayrollDto a un PayrollRecord
     * 
     * @param dto
     * @return
     */
    public static PayrollRecord toRecord(PayrollDto dto) {
	PayrollRecord pr = new PayrollRecord();
	pr.id = dto.id;
	pr.version = dto.version;

	pr.contract_id = dto.contractId;
	pr.date = dto.date;

	// Earnings
	pr.baseSalary = dto.baseSalary;
	pr.extraSalary = dto.extraSalary;
	pr.productivityEarning = dto.productivityEarning;
	pr.trienniumEarning = dto.trienniumEarning;

	// Deductions
	pr.taxDeduction = dto.taxDeduction;
	pr.nicDeduction = dto.nicDeduction;

	// Metadata
	pr.createdAt = LocalDate.now();
	pr.updatedAt = LocalDate.now();
	pr.entityState = "ENABLED";

	return pr;
    }

    public static PayrollSummaryDto toSummaryDto(PayrollRecord pr) {
	PayrollSummaryDto dto = new PayrollSummaryDto();
	dto.id = pr.id;
	dto.date = pr.date;

	double grossSalary = pr.baseSalary + pr.extraSalary
	    + pr.trienniumEarning + pr.productivityEarning;
	double totalDeductions = pr.taxDeduction + pr.nicDeduction;

	dto.netSalary = grossSalary - totalDeductions;

	return dto;
    }

    public static List<PayrollSummaryDto> toSummaryDtoList(
	List<PayrollRecord> records) {
	return records.stream().map(PayrollDtoAssembler::toSummaryDto).toList();
    }

    public static PayrollDto toDto(PayrollRecord pr) {
	PayrollDto dto = new PayrollDto();
	dto.id = pr.id;
	dto.version = pr.version;

	dto.contractId = pr.contract_id;
	dto.date = pr.date;

	dto.baseSalary = pr.baseSalary;
	dto.extraSalary = pr.extraSalary;
	dto.productivityEarning = pr.productivityEarning;
	dto.trienniumEarning = pr.trienniumEarning;

	dto.nicDeduction = pr.nicDeduction;
	dto.taxDeduction = pr.taxDeduction;

	double grossSalary = pr.baseSalary + pr.extraSalary
	    + pr.trienniumEarning + pr.productivityEarning;
	double totalDeductions = pr.nicDeduction + pr.taxDeduction;

	dto.grossSalary = grossSalary;
	dto.totalDeductions = totalDeductions;
	dto.netSalary = grossSalary - totalDeductions;
	return dto;
    }

    /**
     * Convierte un Optional<PayrollRecord> en un Optional<PayrollDto>
     * 
     * @param maybeRecord
     * @return
     */
    public static Optional<PayrollDto> toDtoOptional(
	Optional<PayrollRecord> maybeRecord) {
	return maybeRecord.map(PayrollDtoAssembler::toDto);
    }

}
