package uo.ri.cws.application.ui.manager.contracts.professionalgroup.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteProfessionalGroupAction implements Action {

    @Override
    public void execute() throws BusinessException {
	String name = Console.readString("Professional group name");

	ProfessionalGroupCrudService pg = Factories.service
	    .forProfessionalGroupCrudService();
	pg.delete(name);

	Console.println("The professional group has been deleted");
    }
}