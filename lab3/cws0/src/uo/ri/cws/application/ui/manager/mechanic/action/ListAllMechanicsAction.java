package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListAllMechanicsAction implements Action {

    @Override
    public void execute() throws BusinessException {

	/// 1. Creamos el objeto de negocio
//	ListAllMechanics lm = new ListAllMechanics();
//	lm.execute();

	/// 2. Lo pasamos a la implementación de servicio
//	MechanicCrudService mcs = new MechanicCrudServiceImpl();
//	mcs.findAll();

	// 3. Ahora llamamos a la factoria
	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
	List<MechanicDto> listdto = mcs.findAll();

	// 4. Imprimimos la lista
	Console.println("\nList of mechanics \n");

	for (MechanicDto m : listdto) {
	    Console.printf("\t%s %s %s %s %d\n", m.id, m.name, m.surname, m.nif,
		m.version);
	}
    }
}