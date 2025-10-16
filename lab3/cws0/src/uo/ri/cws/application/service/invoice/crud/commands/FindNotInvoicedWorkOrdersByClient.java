package uo.ri.cws.application.service.invoice.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.crud.InvoiceDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class FindNotInvoicedWorkOrdersByClient
    implements Command<List<InvoicingWorkOrderDto>> {

    private String nif;

    /// Todos los TDGs que necesito
    WorkOrderGateway wg = Factories.persistence.forWorkOrder();

    public FindNotInvoicedWorkOrdersByClient(String nif) {
	ArgumentChecks.isNotNull(nif);
	ArgumentChecks.isNotEmpty(nif);
	this.nif = nif;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() {
	return InvoiceDtoAssembler
	    .toDtoList(wg.findNotInvoicedWorkOrdeByClient(nif));

    }
}
