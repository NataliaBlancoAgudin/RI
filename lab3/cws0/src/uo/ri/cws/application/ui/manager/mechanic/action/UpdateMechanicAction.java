package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

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

	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
	/// Buscamos al mecanico para poder pasarle el nif y la version (esto es
	/// para la UI, porque sino rompe)
	Optional<MechanicDto> m = mcs.findById(id);
	dto.nif = m.get().nif;
	dto.version = m.get().version;

	mcs.update(dto);

	// Print result
	Console.println("Mechanic updated");
    }

}