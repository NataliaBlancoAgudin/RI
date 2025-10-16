package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord> {

    /**
     * Busca las nominas generadas en una fecha dada (normalmente el ultimo dia
     * de cada mes)
     * 
     * @param date fecha exacta de la nomina (ultimo dia)
     * @return lista de payrolls encontrados
     */
    public List<PayrollRecord> findPayrollsByDate(LocalDate date);

    public class PayrollRecord {
	public String id;
	public long version;

	public String contract_id;
	public LocalDate date;

	// Earnings
	public double baseSalary;
	public double extraSalary;
	public double productivityEarning;
	public double trienniumEarning;

	// Deductions
	public double nicDeduction;
	public double taxDeduction;

	// Nuevos valores
	public LocalDate createdAt;
	public String entityState;
	public LocalDate updatedAt;

    }

}
