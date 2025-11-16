package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PayrollJpaRepository extends BaseJpaRepository<Payroll>
    implements PayrollRepository {

    @Override
    public List<Payroll> findByContract(String contractId) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Payroll> findLastMonthPayrolls() {
	LocalDate now = LocalDate.now();
	LocalDate firstDayPrev = now.minusMonths(1).withDayOfMonth(1);
	LocalDate lastDayPrev = now.minusMonths(1)
				   .withDayOfMonth(
				       now.minusMonths(1).lengthOfMonth());

	return Jpa.getManager()
		  .createNamedQuery("Payroll.findByDateBetween", Payroll.class)
		  .setParameter(1, lastDayPrev)
		  .setParameter(2, firstDayPrev)
		  .getResultList();

    }

    @Override
    public Optional<Payroll> findLastPayrollByMechanicId(String mechanicId) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Payroll> findByProfessionalGroupName(String name) {
	return Jpa.getManager()
		  .createNamedQuery("Payroll.findByProfessionalGroupName",
		      Payroll.class)
		  .setParameter(1, name)
		  .getResultList();
    }

    @Override
    public List<Payroll> findByMechanicId(String id) {
	return Jpa.getManager()
		  .createNamedQuery("Payroll.findByMechanicId", Payroll.class)
		  .setParameter(1, id)
		  .getResultList();
    }

    @Override
    public Optional<Payroll> findByContractIdAndDate(String id,
	LocalDate date) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Payroll> findByMechanicIdLastMonth(String mId) {
	LocalDate now = LocalDate.now();
	LocalDate firstDayPrev = now.minusMonths(1).withDayOfMonth(1);
	LocalDate lastDayPrev = now.minusMonths(1)
				   .withDayOfMonth(
				       now.minusMonths(1).lengthOfMonth());

	return Jpa.getManager()
		  .createNamedQuery("Payroll.findByMechanicIdLastMonth",
		      Payroll.class)
		  .setParameter(1, lastDayPrev)
		  .setParameter(2, firstDayPrev)
		  .setParameter(3, mId)
		  .getResultList();
    }

    @Override
    public List<Payroll> findByDate(LocalDate date) {
	return Jpa.getManager()
		  .createNamedQuery("Payroll.findByDate", Payroll.class)
		  .setParameter(1, date)
		  .getResultList();
    }

}
