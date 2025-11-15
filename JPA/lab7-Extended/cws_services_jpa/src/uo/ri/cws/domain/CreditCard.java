package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name="TCreditCards")
public class CreditCard extends PaymentMean {
    @Column(unique = true) private String number;
    @Basic(optional=false) private String type;
    @Basic(optional=false) private LocalDate validThru;
    
    CreditCard(){}

    public CreditCard(String number, String type, LocalDate validThru) {
	ArgumentChecks.isNotBlank(number);
	ArgumentChecks.isNotBlank(type);
	ArgumentChecks.isNotNull(validThru);

	this.number = number;
	this.type = type;
	this.validThru = validThru;
    }

    /**
     * A credit card can pay if is not outdated
     */
    @Override
    public boolean canPay(Double amount) {
	return validThru.isAfter(LocalDate.now());
    }

    /**
     * Augments the accumulated (super.pay(amount) )
     */
    @Override
    public void pay(double amount) {
	StateChecks.isTrue(canPay(amount));
	super.pay(amount);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(number);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!super.equals(obj)) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	CreditCard other = (CreditCard) obj;
	return Objects.equals(number, other.number);
    }

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public LocalDate getValidThru() {
		return validThru;
	}

}
