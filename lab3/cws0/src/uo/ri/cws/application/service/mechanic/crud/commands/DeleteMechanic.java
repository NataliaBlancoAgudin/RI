package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    /// TDGs que necesito
    MechanicGateway mg = Factories.persistence.forMechanic();

    WorkOrderGateway wg = Factories.persistence.forWorkOrder();

    InterventionGateway ig = Factories.persistence.forIntervention();

    ContractGateway cg = Factories.persistence.forContract();

    private String mechanicId;

    public DeleteMechanic(String mechanicId) {
	/// Comprobaciones
	ArgumentChecks.isNotNull(mechanicId);

	this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {

	/// Comprobaciones
	/// 1. Existe mecanico
	Optional<MechanicRecord> om = mg.findById(mechanicId);
	BusinessChecks.exists(om, "The mechanic does not exist");

	/// 2. Existe workorder
	List<String> lw = wg.findByMechanicId(mechanicId);
	BusinessChecks.isEmpty(lw, "Mechanic has assigned workorders");

	/// 3. Existe intervenciones
	List<String> li = ig.findByMechanicId(mechanicId);
	BusinessChecks.isEmpty(li, "Mechanic has assigned interventions");

	/// 4. Existe contract IN FORCE
	List<String> lc = cg.findByMechanicId(mechanicId);
	BusinessChecks.isEmpty(lc, "Mechanic has a contract");

	/// Si sale todo bien
	mg.remove(mechanicId);

	return null;
    }

}
