package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name = "TContracts", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "MECHANIC_ID", "STARTDATE" }) })
public class Contract extends BaseEntity {
    public enum ContractState {
	IN_FORCE, TERMINATED
    }

    // natural attributes
    private LocalDate startDate;
    @Basic(optional = false)
    private LocalDate endDate = null;
    @Basic(optional = false)
    private double annualBaseSalary;
    private double taxRate;
    private double settlement;
    private ContractState state = ContractState.IN_FORCE;

    // accidental attributes
    @ManyToOne
    private Mechanic mechanic;
    @ManyToOne
    private ProfessionalGroup group;
    @ManyToOne
    private ContractType contractType;
    @OneToMany(mappedBy = "contract")
    private Set<Payroll> payrolls = new HashSet<>();

    Contract() {
    }

    public Contract(LocalDate startDate, LocalDate endDate,
	double annualBaseSalary, double taxRate, double settlement,
	Mechanic mechanic, ProfessionalGroup group, ContractType contractType) {
	ArgumentChecks.isNotNull(startDate);
	ArgumentChecks.isTrue(annualBaseSalary >= 0);
	ArgumentChecks.isTrue(taxRate >= 0);
	ArgumentChecks.isTrue(settlement >= 0);
	ArgumentChecks.isNotNull(mechanic);
	ArgumentChecks.isNotNull(group);

	ArgumentChecks.isNotNull(contractType);

	this.startDate = startDate.with(TemporalAdjusters.firstDayOfMonth());
	this.annualBaseSalary = annualBaseSalary;
	this.taxRate = taxRate;
	this.settlement = settlement;

	if (contractType.getName() == "FIXED_TERM") {
	    ArgumentChecks.isNotNull(endDate);
	    ArgumentChecks.isTrue(endDate.isAfter(startDate));
	    this.endDate = endDate.with(TemporalAdjusters.lastDayOfMonth());
	} else {
	    this.endDate = null;
	}

	Associations.Binds.link(this, mechanic);
	Associations.Catogorized.link(this, group);
	Associations.Defines.link(this, contractType);
    }

    public Contract(Mechanic mechanic, ContractType type,
	ProfessionalGroup category, LocalDate signingDate, LocalDate endDate,
	double baseSalary) {
	this(signingDate, endDate, baseSalary, 0.0, 0.0, mechanic, category,
	    type);

	calcTaxRate(baseSalary);
    }

    private void calcTaxRate(double baseSalary) {
	if (baseSalary <= 12450) {
	    this.taxRate = 0.19;
	} else if (baseSalary <= 20200) {
	    this.taxRate = 0.24;
	} else if (baseSalary <= 35200) {
	    this.taxRate = 0.30;
	} else if (baseSalary <= 60000) {
	    this.taxRate = 0.37;
	} else if (baseSalary <= 300000) {
	    this.taxRate = 0.45;
	} else {
	    this.taxRate = 0.47;
	}
    }

    public Contract(Mechanic mechanic, ContractType type,
	ProfessionalGroup group, LocalDate signingDate, double annualSalary) {
	this(signingDate, null, annualSalary, 0.0, 0.0, mechanic, group, type);
    }

    /**
     * terminate: Termina el contrato en el dia pasado como parametro
     * 
     * @param plusDays LocalDate: dia de fecha de finalización
     * 
     *                 Ejemplo de uso: contract.terminate(now)
     */
    public void terminate(LocalDate plusDays) {
	ArgumentChecks.isNotNull(plusDays);
	ArgumentChecks.isTrue(plusDays.isAfter(startDate));
	StateChecks.isTrue(getState().equals(ContractState.IN_FORCE));

	long years = ChronoUnit.YEARS.between(startDate, plusDays);

	// Si no ha llegado al aniversario pero está dentro del mismo mes del
	// aniversario, lo consideramos un año completo.
	if (plusDays.isAfter(startDate.plusMonths(11))) {
	    years = Math.max(1, years);
	}

	this.settlement = contractType.getCompensationDaysPerYear()
	    * (annualBaseSalary / 365) * years;

	this.endDate = plusDays.with(TemporalAdjusters.lastDayOfMonth());
	this.state = ContractState.TERMINATED;
    }

    /**
     * isTerminated: Devuelve true si el contrato es terminado
     * 
     * @return boolean: true en caso de que sea TERMINATED; false en caso
     *         contrario
     * 
     *         Ejemplo de uso: boolean terminado = contract.isTerminated()
     */
    public boolean isTerminated() {
	return state.equals(ContractState.TERMINATED);
    }

    public boolean isInForce() {
	return (!isTerminated())
	    && (endDate == null || endDate.isAfter(LocalDate.now()));
    }

    public Set<Payroll> getPayrolls() {
	return new HashSet<>(payrolls);
    }

    Set<Payroll> _getPayrolls() {
	return payrolls;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    void _setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    public ProfessionalGroup getProfessionalGroup() {
	return group;
    }

    void _setProfessionalGroup(ProfessionalGroup group) {
	this.group = group;
    }

    public ContractType getContractType() {
	return contractType;
    }

    void _setContractType(ContractType type) {
	this.contractType = type;
    }

    public LocalDate getStartDate() {
	return startDate;
    }

    public void setStartDate(LocalDate startDate) {
	this.startDate = startDate;
    }

    public LocalDate getEndDate() {
	return endDate;
    }

    public void setEndDate(LocalDate endDate) {
	this.endDate = endDate;
    }

    public double getAnnualBaseSalary() {
	return annualBaseSalary;
    }

    public void setAnnualBaseSalary(double annualBaseSalary) {
	this.annualBaseSalary = annualBaseSalary;
    }

    public double getTaxRate() {
	return taxRate;
    }

    public void setTaxRate(double taxRate) {
	this.taxRate = taxRate;
    }

    public double getSettlement() {
	return settlement;
    }

    public void setSettlement(double settlement) {
	this.settlement = settlement;
    }

    public ContractState getState() {
	return state;
    }

    public void setState(ContractState state) {
	this.state = state;
    }

    @Override
    public int hashCode() {
	return Objects.hash(mechanic, startDate);
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
	Contract other = (Contract) obj;
	return Objects.equals(mechanic, other.mechanic)
	    && Objects.equals(startDate, other.startDate);
    }

    @Override
    public String toString() {
	return "Contract [startDate=" + startDate + ", endDate=" + endDate
	    + ", annualBaseSalary=" + annualBaseSalary + ", taxRate=" + taxRate
	    + ", settlement=" + settlement + ", state=" + state + ", mechanic="
	    + mechanic + "]";
    }

}
