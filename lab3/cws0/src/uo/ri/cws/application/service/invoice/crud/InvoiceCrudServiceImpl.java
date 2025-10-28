package uo.ri.cws.application.service.invoice.crud;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.crud.commands.FindNotInvoicedWorkOrdersByClient;
import uo.ri.cws.application.service.invoice.crud.commands.InvoiceWorkorder;
import uo.ri.util.exception.BusinessException;

public class InvoiceCrudServiceImpl implements InvoicingService {

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public InvoiceDto create(List<String> workOrderIds)
	throws BusinessException {
//	InvoiceWorkorder iw = new InvoiceWorkorder(workOrderIds);
//	return iw.execute();
	return executor.execute(new InvoiceWorkorder(workOrderIds));
    }

    @Override
    public List<InvoicingWorkOrderDto> findWorkOrdersByClientNif(String nif)
	throws BusinessException {
	// no implementado
	return null;
    }

    @Override
    public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientNif(
	String nif) throws BusinessException {
//	FindNotInvoicedWorkOrdersByClient fni = new FindNotInvoicedWorkOrdersByClient(
//	    nif);
//	return fni.execute();
	return executor.execute(new FindNotInvoicedWorkOrdersByClient(nif));
    }

    @Override
    public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate)
	throws BusinessException {
	// no implementado
	return null;
    }

    @Override
    public Optional<InvoiceDto> findInvoiceByNumber(Long number)
	throws BusinessException {
	// no implementado
	return Optional.empty();
    }

    @Override
    public List<PaymentMeanDto> findPayMeansByClientNif(String nif)
	throws BusinessException {
	// no implementado
	return null;
    }

    @Override
    public void settleInvoice(String invoiceId, Map<String, Double> charges)
	throws BusinessException {
	// no implementado

    }

}
