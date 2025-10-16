package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

	String idMechanic = Console.readString("Type mechanic id ");

	/// 2. Creamos el objeto de negocio
//	DeleteMechanic dm = new DeleteMechanic(idMechanic);
//	dm.execute();

	/// 3. Llamamos a la implementación del servicio
//	MechanicCrudService mcs = new MechanicCrudServiceImpl();
//	mcs.delete(idMechanic);

	/// 4. Ahora llamamos a la factoria
	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
	mcs.delete(idMechanic);

	Console.println("Mechanic deleted");
    }

}