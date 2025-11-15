package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class MechanicJpaRepository
	  extends BaseJpaRepository<Mechanic>
		implements MechanicRepository {

//	@Override
//	public void add(Mechanic t) {
//		Jpa.getManager().persist(t);
//	}
//
//	@Override
//	public void remove(Mechanic t) {
//		Jpa.getManager().remove(t);
//	}
//
//	@Override
//	public Optional<Mechanic> findById(String id) {
//		Mechanic m = Jpa.getManager().find(Mechanic.class, id);
//		return Optional.ofNullable(m);
//	}
//
//	@Override
//	public List<Mechanic> findAll() {
//		String jpql = "SELECT m FROM Mechanic";
//		return Jpa.getManager().
//				createQuery(jpql, Mechanic.class).
//				getResultList();
//	}

	@Override
	public Optional<Mechanic> findByNif(String nif) {
		// aqui no puede estar la query
		// getResultStream puedo tener un metood llamado findFirst que me devuelve un optional
		return Jpa.getManager()
				.createNamedQuery("Mechanic.findByNif", Mechanic.class)
				.setParameter(1, nif)
				.getResultStream()
				.findFirst();
				
	}

}
