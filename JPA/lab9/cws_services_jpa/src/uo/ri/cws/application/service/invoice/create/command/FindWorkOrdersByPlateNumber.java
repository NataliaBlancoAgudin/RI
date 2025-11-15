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

public class FindWorkOrdersByPlateNumber
    implements Command<List<InvoicingWorkOrderDto>> {

    private String plate;

    private WorkOrderRepository wrepo = Factories.repository.forWorkOrder();

    public FindWorkOrdersByPlateNumber(String plate) {
	ArgumentChecks.isNotBlank(plate);

	this.plate = plate;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() throws BusinessException {
	List<WorkOrder> lista = wrepo.findByPlateNumber(plate);

	return DtoAssembler.toInvoicingWorkOrderDtoList(lista);
    }

}
