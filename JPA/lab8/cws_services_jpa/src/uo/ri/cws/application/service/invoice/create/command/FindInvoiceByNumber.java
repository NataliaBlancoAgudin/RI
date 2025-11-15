package uo.ri.cws.application.service.invoice.create.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindInvoiceByNumber implements Command<Optional<InvoiceDto>> {

    private Long number;

    private InvoiceRepository irepo = Factories.repository.forInvoice();

    public FindInvoiceByNumber(Long number) {
	ArgumentChecks.isNotNull(number);

	this.number = number;
    }

    @Override
    public Optional<InvoiceDto> execute() throws BusinessException {
	Optional<Invoice> oinvoice = irepo.findByNumber(number);

	return oinvoice.map(i -> DtoAssembler.toDto(i));
    }

}
