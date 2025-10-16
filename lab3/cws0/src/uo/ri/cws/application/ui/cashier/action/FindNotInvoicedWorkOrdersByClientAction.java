package uo.ri.cws.application.ui.cashier.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindNotInvoicedWorkOrdersByClientAction implements Action {

    /**
     * Process: - Ask customer nif - Display all uncharged workorder (status <>
     * 'INVOICED'). For each workorder, display id, vehicle id, date, status,
     * amount and description
     */

    @Override
    public void execute() throws BusinessException {
	String nif = Console.readString("Client nif ");

	/// 1. Creamos el objeto de negocio
//	FindNotInvoicedWorkOrdersByClient fni = new FindNotInvoicedWorkOrdersByClient(
//	    nif);
//	fni.execute();

	/// 2. Ahora lo haremos con el service
//	InvoicingService ics = new InvoiceCrudServiceImpl();
//	ics.findNotInvoicedWorkOrdersByClientNif(nif);

	/// 3. Ahora lo haremos con la factoria
	InvoicingService ics = Factories.service.forCreateInvoiceService();
	List<InvoicingWorkOrderDto> listDto = ics
	    .findNotInvoicedWorkOrdersByClientNif(nif);

	/// 4. Imprimimos
	Console.println("\nClient's not invoiced work orders\n");
	for (InvoicingWorkOrderDto dto : listDto) {
	    Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n", dto.id,
		dto.description, dto.date, dto.state, dto.amount);
	}
    }

}