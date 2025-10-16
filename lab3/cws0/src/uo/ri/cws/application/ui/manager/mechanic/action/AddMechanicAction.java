package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

	// Get info
	/// 1. Tenemos que encapsular la info en el dto
	MechanicDto dto = new MechanicDto();

	dto.nif = Console.readString("nif");
	dto.name = Console.readString("Name");
	dto.surname = Console.readString("Surname");

	/// 2. Creamos el objeto de negocio
//	AddMechanic am = new AddMechanic(dto);
//	am.execute();

	/// 3. Ahora esto pasará a la implementación del servicio y lo
	/// llamaremos
//	MechanicCrudService mcs = new MechanicCrudServiceImpl();
//	mcs.create(dto);

	/// 4. Ahora llamamos a la factoria
	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
	mcs.create(dto);

	// Print result
	Console.println("Mechanic added: " + dto.id);
    }

}
