package uo.ri.cws.application.service.professionalGroup.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProfessionalGroup implements Command<Void> {

    private String name;

    /// TDGs que necesito
    ProfessionalGroupGateway pg = Factories.persistence.forProfessionalGroup();
    ContractGateway cg = Factories.persistence.forContract();

    public DeleteProfessionalGroup(String name) {
	ArgumentChecks.isNotNull(name);
	ArgumentChecks.isNotBlank(name);

	this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
	/// Comprobaciones
	Optional<ProfessionalGroupRecord> op = pg.findByName(name);
	BusinessChecks.exists(op, "The professionalgroup does not exist");

	String id = op.get().id;
	List<String> plist = cg.findByProfessionalGroupId(id);
	BusinessChecks.isEmpty(plist,
	    "ProfessionalGroup has contracts assigned");

	pg.remove(id);

	return null;
    }

}
