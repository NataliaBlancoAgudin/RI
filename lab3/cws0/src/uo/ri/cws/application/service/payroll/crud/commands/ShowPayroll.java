package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.PayrollDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class ShowPayroll implements Command<Optional<PayrollDto>> {

    private String id;

    // gateways que necesito
    PayrollGateway pg = Factories.persistence.forPayroll();

    public ShowPayroll(String id) {
	ArgumentChecks.isNotNull(id);
	ArgumentChecks.isNotBlank(id);

	this.id = id;
    }

    @Override
    public Optional<PayrollDto> execute() {
	return PayrollDtoAssembler.toDtoOptional(pg.findById(id));
    }
}
