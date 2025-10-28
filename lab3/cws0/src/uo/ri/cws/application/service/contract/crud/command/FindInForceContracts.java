package uo.ri.cws.application.service.contract.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.ContractDtoAssembler;

public class FindInForceContracts implements Command<List<ContractDto>> {

    // gateways que necesito
    ContractGateway cg = Factories.persistence.forContract();

    @Override
    public List<ContractDto> execute() {
	return ContractDtoAssembler.toDtoList(cg.findInForceContracts());
    }

}
