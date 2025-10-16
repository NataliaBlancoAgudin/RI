package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

	// Get info
	String nif = Console.readString("nif");

	/// 1. Creamos el objeto de negocio
//	ListMechanic lm = new ListMechanic(nif);
//	lm.execute();

	/// 2. Ahora lo pasamos a la implementación de servicio
//	MechanicCrudService mcs = new MechanicCrudServiceImpl();
//	mcs.findById(nif);

	/// 3. Ahora llamamos a la factoria
	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
	Optional<MechanicDto> omdto = mcs.findByNif(nif);

	Console.println("\nMechanic information \n");

	// 4. Imprimimos si existe información
	if (omdto.isPresent()) {
	    MechanicDto dto = omdto.get();
	    Console.printf("\t%s %s %s %s %d\n", dto.id, dto.name, dto.surname,
		dto.nif, dto.version);
	} else {
	    Console.print("No existe el mecánico con el nif: " + nif);
	}
    }
}