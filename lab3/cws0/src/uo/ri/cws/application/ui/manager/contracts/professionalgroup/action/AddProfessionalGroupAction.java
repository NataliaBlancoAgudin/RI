package uo.ri.cws.application.ui.manager.contracts.professionalgroup.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddProfessionalGroupAction implements Action {

    @Override
    public void execute() throws BusinessException {

	String name = Console.readString("Professional group name");
	double trienniumPayment = Console.readDouble("Triennium payment");
	double productivityRate = Console.readDouble("Productivity rate");

	/// 1. Creamos el dto
	ProfessionalGroupDto dto = new ProfessionalGroupDto();
	dto.name = name;
	dto.trienniumPayment = trienniumPayment;
	dto.productivityRate = productivityRate;

	/// 2. Creamos el servicio
	ProfessionalGroupCrudService pg = Factories.service
	    .forProfessionalGroupCrudService();

	/// 3. Ejecutamos el create
	pg.create(dto);

	Console.println("Professional group registered");
    }
}