package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TMechanics")
public class Mechanic extends BaseEntity {
    // natural attributes
    @Column(unique = true)
    private String nif;
    @Basic(optional = false)
    private String surname;
    @Basic(optional = false)
    private String name;

    // accidental attributes
    @OneToMany(mappedBy = "mechanic")
    private Set<WorkOrder> assigned = new HashSet<>();
    @OneToMany(mappedBy = "mechanic")
    private Set<Intervention> interventions = new HashSet<>();

    @OneToMany(mappedBy = "mechanic")
    private Set<Contract> contracts = new HashSet<>();

    Mechanic() {
    }

    public Mechanic(String nif, String surname, String name) {
	ArgumentChecks.isNotBlank(nif);
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isNotBlank(surname);

	this.nif = nif;
	this.surname = surname;
	this.name = name;
    }

    public Mechanic(String nif) {
	this(nif, "no-surname", "no-name");
    }

    /**
     * getContractInForce: devuelve el contrato IN_FORCE del mecanico
     * 
     * @return Optional Contract
     * 
     *         Ejemplo de uso: Optional<Contract> contract =
     *         getContractInForce()
     */
    public Optional<Contract> getContractInForce() {
	// si no tiene contratos asignados devuelve el optional vacio
	if (contracts.isEmpty()) {
	    return Optional.empty();
	}

	// comprobamos si existe mas de un contrato en vigor
	Contract newContract = contractsInForce();

	// si no hay contratos en vigor devuelve el optional vacio
	if (newContract == null) {
	    return Optional.empty();
	}

	// si existe mas de un contrato en vigor, los terminamos todos excepto
	// el mas reciente
	terminatedContracts(newContract);
	return Optional.of(newContract);
    }

    /**
     * terminatedContracts: Termina todos los contratos en vigor excepto el mas
     * reciente (el pasado como parametro)
     * 
     * @param contract Contract: Contrato más reciente
     * 
     *                 Ejemplo de uso: terminatedContracts(contract)
     */
    private void terminatedContracts(Contract contract) {
	for (Contract c : contracts) {
	    if (!c.equals(contract) && c.isInForce()) {
		c.terminate(LocalDate.now());
	    }
	}
    }

    /**
     * contractsInForce: Devuelve el contrato mas reciente que esta en vigor
     * 
     * @return Contract - Contrato mas reciente en vigor
     * 
     *         Ejemplo de uso: Contract contract = contractsInForce()
     */
    private Contract contractsInForce() {
	Contract newContract = null;
	for (Contract c : contracts) {
	    if (c.isInForce()) {
		if (newContract == null
		    || c.getStartDate().isAfter(newContract.getStartDate())) {
		    newContract = c;
		}
	    }
	}
	return newContract;
    }

    public Set<Contract> getContracts() {
	return new HashSet<>(contracts);
    }

    Set<Contract> _getContracts() {
	return contracts;
    }

    public Set<WorkOrder> getAssigned() {
	return new HashSet<>(assigned);
    }

    Set<WorkOrder> _getAssigned() {
	return assigned;
    }

    public Set<Intervention> getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

    public String getNif() {
	return nif;
    }

    public String getSurname() {
	return surname;
    }

    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	return Objects.hash(nif);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Mechanic other = (Mechanic) obj;
	return Objects.equals(nif, other.nif);
    }

    @Override
    public String toString() {
	return "Mechanic [nif=" + nif + ", surname=" + surname + ", name="
	    + name + "]";
    }

}
