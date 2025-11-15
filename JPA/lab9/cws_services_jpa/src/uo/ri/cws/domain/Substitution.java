package uo.ri.cws.domain;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(
		name="TSubstitutions",
		uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"SPAREPART_ID", "INTERVENTION_ID"
		})
})
public class Substitution extends BaseEntity{
    // natural attributes
	@Basic(optional=false) private int quantity;

    // accidental attributes
    @ManyToOne private SparePart sparePart;
    @ManyToOne private Intervention intervention;
    
    Substitution(){}

    public Substitution(SparePart sparePart, Intervention intervention,
	int quantity) {
	ArgumentChecks.isNotNull(sparePart);
	ArgumentChecks.isNotNull(intervention);
	ArgumentChecks.isTrue(quantity > 0);

//	this.intervention = intervention;
//	this.sparePart = sparePart;
	this.quantity = quantity;
	Associations.Substitutes.link(sparePart, this, intervention);
    }

    public double getAmount() {
	return quantity * sparePart.getPrice();
    }

    public int getQuantity() {
	return quantity;
    }

    public SparePart getSparePart() {
	return sparePart;
    }

    public Intervention getIntervention() {
	return intervention;
    }

    void _setSparePart(SparePart sparePart) {
	this.sparePart = sparePart;
    }

    void _setIntervention(Intervention intervention) {
	this.intervention = intervention;
    }

    @Override
    public int hashCode() {
	return Objects.hash(intervention, sparePart);
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
	Substitution other = (Substitution) obj;
	return Objects.equals(intervention, other.intervention)
	    && Objects.equals(sparePart, other.sparePart);
    }

    @Override
    public String toString() {
	return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart
	    + ", intervention=" + intervention + "]";
    }

}
