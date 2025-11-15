package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TProfessionalGroups")
public class ProfessionalGroup extends BaseEntity {

    // natural attributes
    @Column(unique = true)
    private String name;
    private double trienniumPayment;
    private double productivityRate;

    // accidental attributes
    @OneToMany(mappedBy = "professionalgroup")
    private Set<Contract> contracts = new HashSet<>();

    public ProfessionalGroup() {
    }

    public ProfessionalGroup(String name, double trienniumPayment,
	double productivityRate) {
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isTrue(trienniumPayment >= 0);
	ArgumentChecks.isTrue(productivityRate >= 0);

	this.name = name;
	this.trienniumPayment = trienniumPayment;
	this.productivityRate = productivityRate;
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

    public double getTrienniumPayment() {
	return trienniumPayment;
    }

    public void setTrienniumPayment(double trienniumPayment) {
	this.trienniumPayment = trienniumPayment;
    }

    public double getProductivityRate() {
	return productivityRate;
    }

    public void setProductivityRate(double productivityRate) {
	this.productivityRate = productivityRate;
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
	ProfessionalGroup other = (ProfessionalGroup) obj;
	return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
	return "ProfessionalGroup [name=" + name + ", trienniumPayment="
	    + trienniumPayment + ", productivityRate=" + productivityRate + "]";
    }

}
