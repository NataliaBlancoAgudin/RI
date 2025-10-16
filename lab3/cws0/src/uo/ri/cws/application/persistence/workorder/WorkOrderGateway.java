package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkorderRecord;

public interface WorkOrderGateway extends Gateway<WorkorderRecord> {

    /**
     * Lista de InvoicingWorkOrderRecord por el nif del cliente
     * 
     * @param nif
     * @return
     */
    public List<InvoicingWorkOrderRecord> findNotInvoicedWorkOrdeByClient(
	String nif);

    /**
     * Busca las workorders de un mecanico dado
     * 
     * @param id
     * @return
     */
    public List<String> findByMechanicId(String id);

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
