package uo.ri.cws.domain;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name = "TVouchers")
public class Voucher extends PaymentMean {
    @Column(unique = true)
    private String code;
    @Basic(optional = false)
    private double available = 0.0;
    @Basic(optional = false)
    private String description;

    Voucher() {
    }

    public Voucher(String code, String description, double available) {
	ArgumentChecks.isNotBlank(code);
	ArgumentChecks.isNotNull(available);
	ArgumentChecks.isTrue(available >= 0);

	this.code = code;
	this.available = available;
	this.description = description;
    }

    /**
     * Augments the accumulated (super.pay(amount) ) and decrements the
     * available
     * 
     * @throws IllegalStateException if not enough available to pay
     */
    @Override
    public void pay(double amount) {
	StateChecks.isTrue(canPay(amount));
	super.pay(amount);
	available -= amount;
    }

    /**
     * A voucher can pay if it has enough available to pay the amount
     */
    @Override
    public boolean canPay(Double amount) {
	if (amount <= available) {
	    return true;
	}
	return false;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(code);
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
	Voucher other = (Voucher) obj;
	return Objects.equals(code, other.code);
    }

    public String getCode() {
	return code;
    }

    public String getDescription() {
	return description;
    }

    public double getAvailable() {
	return available;
    }

}
