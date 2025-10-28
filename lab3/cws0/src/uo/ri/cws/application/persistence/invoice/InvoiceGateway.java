package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

    /**
     * findNextNumber: Encuentra el ultimo numero (invoice identity) de la tabla
     * invoice
     * 
     * @return long
     * 
     *         Ejemplo de uso: Long number = invoiceGateway.findNextNumber();
     */
    public long findNextNumber();

    public static class InvoiceRecord {

	public String id; // the surrogate id (UUID)
	public long version;
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;
	public String entityState;

	public double amount; // total amount (money) vat included
	public double vat; // amount of vat (money)
	public long number; // the invoice identity, a sequential number
	public LocalDate date; // of the invoice
	public String state; // the state as in InvoiceState
    }

    public static class InvoicingWorkOrderRecord {
	public String id;
	public String description;
	public LocalDateTime date;
	public String state;
	public double amount;
    }

}
