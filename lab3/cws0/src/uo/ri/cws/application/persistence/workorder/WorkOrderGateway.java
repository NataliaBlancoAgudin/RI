package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkorderRecord;

public interface WorkOrderGateway extends Gateway<WorkorderRecord> {

    /**
     * findNotInvoiceWorkOrderByClient: Lista de InvoicingWorkOrderRecord por el
     * nif del cliente
     * 
     * @param nif String: nombre del cliente
     * @return List InvoicingWorkOrderRecord
     * 
     *         Ejemplo de uso: List<InvoicingWorkOrderRecord> lista =
     *         workorderGateway.findNotInvoicedWorkOrdeByClient("nif")
     */
    public List<InvoicingWorkOrderRecord> findNotInvoicedWorkOrdeByClient(
	String nif);

    /**
     * findByMechanicId: Busca las workorders de un mecanico dado
     * 
     * @param id String: Id del mecanico
     * @return List String
     * 
     *         Ejemplo de uso: List<String> lista =
     *         workorderGatway.findByMechanicId("mechanicId");
     */
    public List<String> findByMechanicId(String id);

    /**
     * findTotalInvoicedForMechanicInMonth: Devuelve la suma total de los
     * importes de las workorders reparadas por el mecanico en un mes dado
     * 
     * @param mechanicId String: Id del mecanico
     * @param date       LocalDate: fecha
     * @return double
     * 
     *         Ejemplo de uso: double totalWorkordersAmount = workorderGateway
     *         .findTotalInvoicedForMechanicInMonth("mechanic_id", date);
     */
    public double findTotalInvoicedForMechanicInMonth(String mechanicId,
	LocalDate date);

    /// FOTOCOPIA DE LA BASE DE DATOS
    public class WorkorderRecord {
	public String id;
	public double amount;
	public LocalDateTime createdAt;
	public LocalDateTime date;
	public String description;
	public String entityState;
	public String state;
	public LocalDateTime updatedAt;
	public long version;

	public String invoice_id;
	public String mechanic_id;
	public String vehicle_id;

    }

}
