package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class WorkOrder {
    public enum WorkOrderState {
	OPEN, ASSIGNED, FINISHED, INVOICED
    }

    // natural attributes
    private LocalDateTime date;
    private String description;
    private double amount = 0.0;
    private WorkOrderState state = WorkOrderState.OPEN;

    // accidental attributes
    private Vehicle vehicle;
    private Mechanic mechanic;
    private Invoice invoice;
    private Set<Intervention> interventions = new HashSet<>();

    public WorkOrder(Vehicle vehicle, String descripcion) {
	this(vehicle, LocalDateTime.now(), descripcion);
    }

    // FULL CONSTRUCTOR
    public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
	ArgumentChecks.isNotNull(vehicle);
	ArgumentChecks.isNotNull(date);
	ArgumentChecks.isNotBlank(description);

	// No me vale así
//		this.vehicle = vehicle;
	this.date = date.truncatedTo(ChronoUnit.MILLIS);
	this.description = description;
	// OJO ES IMPORTANTE EL ORDEN PORQUE EL ID DE WORKORDER ES DATE +
	// VEHICLE
	Associations.Fixes.link(vehicle, this);
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime date) {
	this(vehicle, date, "no-description");
    }

    public WorkOrder(Vehicle vehicle) {
	this(vehicle, LocalDateTime.now(), "no-description");
    }

    public LocalDateTime getDate() {
	return date;
    }

    public String getDescription() {
	return description;
    }

    public double getAmount() {
	if (isOpen() || isAssigned()) {
	    return 0.0;
	}
	return amount;
    }

    public WorkOrderState getState() {
	return state;
    }

    public Vehicle getVehicle() {
	return vehicle;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    public Invoice getInvoice() {
	return invoice;
    }

    public Set<Intervention> getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

    void _setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
    }

    void _setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    void _setInvoice(Invoice invoice) {
	this.invoice = invoice;
    }

    /// aqui puedo meter vehicle porque es identidad (aunque sea accidental)
    @Override
    public String toString() {
	return "WorkOrder [date=" + date + ", description=" + description
	    + ", amount=" + amount + ", state=" + state + ", vehicle=" + vehicle
	    + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(date, vehicle);
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
	WorkOrder other = (WorkOrder) obj;
	return Objects.equals(date, other.date)
	    && Objects.equals(vehicle, other.vehicle);
    }

    /**
     * Changes it to INVOICED state given the right conditions This method is
     * called from Invoice.addWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not FINISHED, or -
     *                               The work order is not linked with the
     *                               invoice
     */
    public void markAsInvoiced() {
	StateChecks.isTrue(isFinished());
	StateChecks.isNotNull(this.invoice);

	this.state = WorkOrderState.INVOICED;
    }

    /**
     * Given the right conditions unlinks the workorder and the mechanic,
     * changes the state to FINISHED and computes the amount
     *
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state, or
     */
    public void markAsFinished() {
	StateChecks.isTrue(isAssigned());

	Associations.Assigns.unlink(mechanic, this);
	computeAmount();
	this.state = WorkOrderState.FINISHED;
    }

    private void computeAmount() {
	double total = 0.0;

	for (Intervention i : interventions) {
	    total += i.getAmount();
	}

	this.amount = total;
    }

    /**
     * Changes it back to FINISHED state given the right conditions This method
     * is called from Invoice.removeWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not INVOICED, or
     */
    public void markBackToFinished() {
	StateChecks.isTrue(isInvoiced());

	this.state = WorkOrderState.FINISHED;
    }

    /**
     * Links (assigns) the work order to a mechanic and then changes its state
     * to ASSIGNED
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in OPEN state,
     *                               or
     */
    public void assignTo(Mechanic mechanic) {
	ArgumentChecks.isNotNull(mechanic);
	StateChecks.isTrue(isOpen());

	Associations.Assigns.link(mechanic, this);
	this.state = WorkOrderState.ASSIGNED;
    }

    /**
     * Unlinks (deassigns) the work order and the mechanic and then changes its
     * state back to OPEN
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state
     */
    public void unassign() {
	StateChecks.isTrue(isAssigned());

	Associations.Assigns.unlink(mechanic, this);
	this.state = WorkOrderState.OPEN;
    }

    /**
     * In order to assign a work order to another mechanic it first have to be
     * moved back to OPEN state.
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in FINISHED
     *                               state
     */
    public void reopen() {
	StateChecks.isTrue(isFinished());
	this.state = WorkOrderState.OPEN;
	this.amount = 0;

    }

    public boolean isFinished() {
	if (state.equals(WorkOrderState.FINISHED)) {
	    return true;
	}
	return false;
    }

    public boolean isAssigned() {
	if (state.equals(WorkOrderState.ASSIGNED)) {
	    return true;
	}
	return false;
    }

    public boolean isOpen() {
	if (state.equals(WorkOrderState.OPEN)) {
	    return true;
	}
	return false;
    }

    public boolean isInvoiced() {
	if (state.equals(WorkOrderState.INVOICED)) {
	    return true;
	}
	return false;
    }

}
