package uo.ri.cws.application.service.invoice.crud.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkorderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.crud.InvoiceDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.math.Rounds;

public class InvoiceWorkorder implements Command<InvoiceDto> {

    /// TDGs que necesito
    WorkOrderGateway wg = Factories.persistence.forWorkOrder();

    InvoiceGateway ig = Factories.persistence.forInvoice();

    private List<String> workOrderIds;

    public InvoiceWorkorder(List<String> workOrderIds) {
	ArgumentChecks.isNotNull(workOrderIds);

	checkWorkOrderIds(workOrderIds);

	this.workOrderIds = workOrderIds;
    }

    private void checkWorkOrderIds(List<String> workOrderIds) {
	if (workOrderIds.isEmpty()) {
	    throw new IllegalArgumentException(
		"La lista de WorkOrdersIds no puede ser vacía");
	}

	for (String w : workOrderIds) {
	    if (w == null) {
		throw new IllegalArgumentException(
		    "La lista de WorkOrdersIds no puede contener valores null");
	    }
	}
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
	try (Connection ignored = Jdbc.createThreadConnection()) {

	    if (!checkWorkOrdersExist(workOrderIds)) {
		throw new BusinessException("Workorder does not exist");
	    }
	    if (!checkWorkOrdersFinished(workOrderIds)) {
		throw new BusinessException("Workorder is not finished yet");
	    }

	    long numberInvoice = generateInvoiceNumber();
	    LocalDate dateInvoice = LocalDate.now();
	    double amount = calculateTotalInvoice(workOrderIds); // vat not
								 // included
	    double vat = vatPercentage(dateInvoice);
	    double vatAmount = amount * (vat / 100); // vat amount
	    double total = amount * vatAmount; // vat included
	    total = Rounds.toCents(total);

	    String idInvoice = createInvoice(numberInvoice, dateInvoice,
		vatAmount, total);
	    updateWorkorder(idInvoice, workOrderIds);

	    /// Creamos el dto
	    InvoiceDto idto = new InvoiceDto();
	    idto.id = idInvoice;
	    idto.version = 1L;
	    idto.amount = total;
	    idto.vat = vatAmount;
	    idto.number = numberInvoice;
	    idto.date = dateInvoice;
	    idto.state = "NOT_YET_PAID";
	    return idto;

	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

    }

    /*
     * checks whether every work order exist
     */
    private boolean checkWorkOrdersExist(List<String> workOrderIDS)
	throws SQLException {

	for (String workOrderID : workOrderIDS) {
	    Optional<WorkorderRecord> ow = wg.findById(workOrderID);
	    if (ow.isEmpty()) {
		return false;
	    }
	}
	return true;
    }

    /*
     * checks whether every work order id is FINISHED
     */
    private boolean checkWorkOrdersFinished(List<String> workOrderIDs)
	throws SQLException {

	for (String id : workOrderIDs) {
	    Optional<WorkorderRecord> ow = wg.findById(id);

	    if (!ow.isEmpty()) {
		if (!"FINISHED".equalsIgnoreCase(ow.get().state)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /*
     * Generates next invoice number (not to be confused with the inner id)
     */
    private long generateInvoiceNumber() throws SQLException {
	return ig.findNextNumber();
    }

    /*
     * Compute total amount of the invoice (as the total of individual work
     * orders' amount
     */
    private double calculateTotalInvoice(List<String> workOrderIDs)
	throws SQLException {

	double total = 0.0;

	for (String id : workOrderIDs) {
	    Optional<WorkorderRecord> ow = wg.findById(id);
	    if (!ow.isEmpty()) {
		total += ow.get().amount;
	    }
	}
	return total;
    }

    /*
     * returns vat percentage
     */
    private double vatPercentage(LocalDate d) {
	return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;

    }

    /*
     * Creates the invoice in the database; returns the id
     */
    private String createInvoice(long numberInvoice, LocalDate dateInvoice,
	double vat, double total) throws SQLException {
	InvoiceDto dto = new InvoiceDto();
	dto.id = UUID.randomUUID().toString();
	dto.number = numberInvoice;
	dto.date = dateInvoice;
	dto.vat = vat;
	dto.amount = total;
	dto.state = "NOT_YET_PAID";

	ig.add(InvoiceDtoAssembler.toRecord(dto));

	return dto.id;
    }

    /**
     * Actuliza la workorder: - Pone el invoice number en el campo invoice_id -
     * Pone el estado INVOICED - Actualiza la version - Actuliza el updatedat
     * 
     * @param idInvoice
     * @param workOrderIds
     */
    private void updateWorkorder(String idInvoice, List<String> workOrderIds) {
	for (String id : workOrderIds) {
	    WorkorderRecord wr = new WorkorderRecord();
	    wr.id = id;
	    wr.invoice_id = idInvoice;
	    wg.update(wr);
	}
    }

}
