package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindWorkOrderByClientNif
    implements Command<List<InvoicingWorkOrderDto>> {

    private String nif;

    private WorkOrderRepository wrepo = Factories.repository.forWorkOrder();

    public FindWorkOrderByClientNif(String nif) {
	ArgumentChecks.isNotBlank(nif);

	this.nif = nif;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() throws BusinessException {
	List<WorkOrder> lista = wrepo.findByClientNif(nif);

	return DtoAssembler.toInvoicingWorkOrderDtoList(lista);
    }

}
