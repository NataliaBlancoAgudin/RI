package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class ContractType extends BaseEntity {
    // natural attributes
    @Basic(optional = false)
    private String name;
    private double compensationDaysPerYear;

    // accidental attributes
    @OneToMany(mappedBy = "contractType")
    private Set<Contract> contracts = new HashSet<>();

    public ContractType() {
    }

    public ContractType(String name, double compensationDaysPerYear) {
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isTrue(compensationDaysPerYear >= 0);

	this.name = name;
	this.compensationDaysPerYear = compensationDaysPerYear;
    }

    public Set<Contract> getContracts() {
	return new HashSet<>(contracts);
    }

    Set<Contract> _getContracts() {
	return contracts;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public double getCompensationDaysPerYear() {
	return compensationDaysPerYear;
    }

    public void setCompensationDaysPerYear(double compensationDaysPerYear) {
	this.compensationDaysPerYear = compensationDaysPerYear;
    }

    @Override
    public int hashCode() {
	return Objects.hash(name);
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
	ContractType other = (ContractType) obj;
	return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
	return "ContractType [name=" + name + ", compensationDaysPerYear="
	    + compensationDaysPerYear + "]";
    }

}
