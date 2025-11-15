package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class CreateInvoiceFor implements Command<InvoiceDto> {

    private List<String> workOrderIds;

    private InvoiceRepository invoiceRepo = Factories.repository.forInvoice();
    private WorkOrderRepository workorderRepo = Factories.repository.forWorkOrder();

    public CreateInvoiceFor(List<String> workOrderIds) {
	ArgumentChecks.isNotNull(workOrderIds);
	ArgumentChecks.isFalse(workOrderIds.isEmpty());
	ArgumentChecks.isFalse(workOrderIds.stream().anyMatch(i -> i == null));

	this.workOrderIds = workOrderIds;
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
	// Pasamos la lista de workorder
	List<WorkOrder> workOrderList = workorderRepo.findByIds(workOrderIds);

	// Deberemos de comprobar que las workorders existan
	BusinessChecks.isTrue(workOrderIds.size() == workOrderList.size(),
	    "Deben de existir todas las workorders");
	// Deben de estar todas a finished
	BusinessChecks.isTrue(allFinished(workOrderList),
	    "Todas las workorders deben de estar finalizadas");

	// Calculamos el numero del la nueva invoice
	long number = invoiceRepo.getNextInvoiceNumber();

	// la creamos y la añadimos
	Invoice i = new Invoice(number, workOrderList);
	invoiceRepo.add(i);
	return DtoAssembler.toDto(i);
    }

    private boolean allFinished(List<WorkOrder> workorders) {
	for (WorkOrder w : workorders) {
	    if (!w.isFinished()) {
		return false;
	    }
	}
	return true;
    }

}
