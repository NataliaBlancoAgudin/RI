package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.menu.Action;

public class ListMechanicInForceContractAction implements Action {

    @Override
    public void execute() throws Exception {
	ContractCrudService cs = Factories.service.forContractCrudService();
	MechanicCrudService ms = Factories.service.forMechanicCrudService();

	List<ContractDto> contracts = cs.findInforceContracts();

	List<MechanicDto> mechanicsOfContracts = new ArrayList<>();
	for (ContractDto dto : contracts) {
	    Optional<MechanicDto> m = ms.findById(dto.mechanic.id);
	    mechanicsOfContracts.add(m.get());
	}

	for (MechanicDto dto : mechanicsOfContracts) {
	    Printer.printMechanic(dto);
	}
    }

}
