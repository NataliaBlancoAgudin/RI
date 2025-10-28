package uo.ri.cws.application.ui.manager.contracts.professionalgroup.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateProfessionalGroupAction implements Action {

    @Override
    public void execute() throws BusinessException {
	String name = Console.readString("Professional group name");

	double trienniumPayment = Console.readDouble("Triennium payment");
	double productivityRate = Console.readDouble("Productivity rate");

	ProfessionalGroupDto dto = new ProfessionalGroupDto();
	dto.name = name;
	dto.trienniumPayment = trienniumPayment;
	dto.productivityRate = productivityRate;

	ProfessionalGroupCrudService pgs = Factories.service
	    .forProfessionalGroupCrudService();
	/// Cogemos la version antes de actulizar porque sino rompe en la UI
	Optional<ProfessionalGroupDto> op = pgs.findByName(name);
	dto.version = op.get().version;

	pgs.update(dto);

	Console.println("Professional group updated");

    }
}