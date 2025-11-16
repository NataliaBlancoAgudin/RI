package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProfessionalGroup implements Command<Void> {

    private String name;

    private ProfessionalGroupRepository pgrepo = Factories.repository.forProfessionalGroup();

    public DeleteProfessionalGroup(String name) {
	ArgumentChecks.isNotBlank(name);

	this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroup> opg = pgrepo.findByName(name);
	BusinessChecks.exists(opg, "The professional group does not exists");

	ProfessionalGroup pg = opg.get();
	BusinessChecks.isTrue(pg.getContracts().isEmpty(),
	    "The professional group has contracts");

	pgrepo.remove(pg);

	return null;
    }

}
