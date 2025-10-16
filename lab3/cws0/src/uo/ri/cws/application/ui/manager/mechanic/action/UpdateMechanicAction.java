package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

	/// 1. Creamos el dto
	MechanicDto dto = new MechanicDto();
	// Get info
	String id = Console.readString("Type mechahic id to update");
	// Ask for new data
	// nif is the identity, cannot be changed
	String name = Console.readString("Name");
	String surname = Console.readString("Surname");
	dto.id = id;
	dto.name = name;
	dto.surname = surname;

	/// 1. Creamos el objeto de negocio
//	UpdateMechanic um = new UpdateMechanic(dto);
//	um.execute();

	/// 2. Ahora se pasará a la impleementación del servicio
//	MechanicCrudService mcs = new MechanicCrudServiceImpl();
//	mcs.update(dto);

	/// 3. Ahora lo llamamos en la factoria
	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
	mcs.update(dto);

	// Print result
	Console.println("Mechanic updated");
    }

}