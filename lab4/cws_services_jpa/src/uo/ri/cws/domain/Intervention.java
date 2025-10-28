package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Intervention {
    // natural attributes
    private LocalDateTime date;
    private int minutes;

    // accidental attributes
    private WorkOrder workOrder;
    private Mechanic mechanic;
    private Set<Substitution> substitutions = new HashSet<>();

    public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
	this(workOrder, mechanic, LocalDateTime.now(), minutes);
    }

    public Intervention(WorkOrder workOrder, Mechanic mechanic,
	LocalDateTime date, int minutes) {
	ArgumentChecks.isNotNull(workOrder);
	ArgumentChecks.isNotNull(mechanic);
	ArgumentChecks.isNotNull(date);
	ArgumentChecks.isTrue(minutes >= 0);

//	this.workOrder = workOrder;
//	this.mechanic = mechanic;
	this.date = date.truncatedTo(ChronoUnit.MILLIS);
	this.minutes = minutes;
	Associations.Intervenes.link(workOrder, this, mechanic);
    }

    public LocalDateTime getDate() {
	return date;
    }

    public int getMinutes() {
	return minutes;
    }

    public WorkOrder getWorkOrder() {
	return workOrder;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    void _setWorkOrder(WorkOrder workOrder) {
	this.workOrder = workOrder;
    }

    void _setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    public Set<Substitution> getSubstitutions() {
	return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
	return substitutions;
    }

    @Override
    public int hashCode() {
	return Objects.hash(date, mechanic, workOrder);
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
	Intervention other = (Intervention) obj;
	return Objects.equals(date, other.date)
	    && Objects.equals(mechanic, other.mechanic)
	    && Objects.equals(workOrder, other.workOrder);
    }

    @Override
    public String toString() {
	return "Intervention [date=" + date + ", minutes=" + minutes
	    + ", workOrder=" + workOrder + ", mechanic=" + mechanic + "]";
    }

    public double getAmount() {
	double total = 0.0;

	// 1. mano de obra
	double laborPrice = getWorkorderAmount();

	// 2. Precio presupuesto
	double substitutionAmount = getSubstitutionsAmount(total);

	total = laborPrice + substitutionAmount;

	return total;
    }

    private double getSubstitutionsAmount(double total) {
	for (Substitution s : substitutions) {
	    total += s.getAmount();
	}
	return total;
    }

    private double getWorkorderAmount() {
	VehicleType vt = workOrder.getVehicle().getVehicleType();
	double laborPrice = vt.getPricePerHour() * (minutes / 60);
	return laborPrice;
    }

}
