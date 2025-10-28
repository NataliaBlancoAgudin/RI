package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord> {

    /**
     * findPayrollsByDate: Busca las nominas generadas en una fecha dada
     * (normalmente el ultimo dia de cada mes)
     * 
     * @param date LocalDate: fecha exacta de la nomina (ultimo dia)
     * @return List PayrollRecord - lista de payrolls encontrados
     * 
     *         Ejemplo de uso: List<PayrollRecord> lista =
     *         payrollGateway.findPayrollsByDate(lastMonth);
     */
    public List<PayrollRecord> findPayrollsByDate(LocalDate date);

    /**
     * findPayrollsOfMechanicByDate: Busca las nominas generadas en una fecha
     * dada y de un mecanico pasado como parametro
     * 
     * @param mechanicId String: Id del mecanico
     * @param date       LocalDate: fecha
     * @return List PayrollRecord
     * 
     *         Ejemplo de uso: List<PayrollRecord> lista =
     *         payrollGatway.findPayrollsOfMechanicByDate("mechanicId",
     *         lastMonth);
     */
    public List<PayrollRecord> findPayrollsOfMechanicByDate(String mechanicId,
	LocalDate date);

    /**
     * findPayrollsByMechanicId: Busca TODAS las nominas del mecanico pasado
     * como parametro
     * 
     * @param mechanicId String: Id del mecanico
     * @return List PayrollRecord
     * 
     *         Ejemplo de uso: List<PayrollRecord> lista =
     *         payrollGateway.findPayrollsByMechanicId("mechanicId");
     */
    public List<PayrollRecord> findPayrollsByMechanicId(String mechanicId);

    /**
     * findPayrollsByProfessionalGroupName Busca TODAS las nominas de un
     * professional group pasado como parametro
     * 
     * @param groupName String: Nombre del Professional Group
     * @return List PayrollRecord
     * 
     *         Ejemplo de uso: List<PayrollRecord> lista = payrollGateway
     *         .findPayrollsByProfessionalGroupName("groupName");
     */
    public List<PayrollRecord> findPayrollsByProfessionalGroupName(
	String groupName);

    /**
     * countPayrollsByContractId: Cuenta el numero de payrolls de un contrato
     * 
     * @param contract_id String: Id del contrato
     * @return int
     * 
     *         Ejemplo de uso: int numero =
     *         payrollGatway.countPayrollsByContractId("contract_id")
     */
//    public int countPayrollsByContractId(String contract_id);

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
