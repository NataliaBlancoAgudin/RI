package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ContractJpaRepository extends BaseJpaRepository<Contract>
    implements ContractRepository {

    @Override
    public List<Contract> findAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Contract> findAllInForce() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Contract> findByMechanicId(String id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Contract> findByProfessionalGroupId(String id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Contract> findByContractTypeId(String id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Contract> findAllInForceThisMonth(LocalDate present) {

	LocalDate endDate = present.minusMonths(1)
				   .withDayOfMonth(
				       present.minusMonths(1).lengthOfMonth());

	return Jpa.getManager()
		  .createNamedQuery("Contract.findInForceBetween",
		      Contract.class)
		  .setParameter(1, endDate)
		  .getResultList();
    }

    @Override
    public List<Contract> findInforceContracts() {
	return Jpa.getManager()
		  .createNamedQuery("Contract.findWithContractInForce",
		      Contract.class)
		  .getResultList();
    }

}
