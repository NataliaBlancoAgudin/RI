package uo.ri.cws.application.persistence.contract;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {

    /**
     * Busca contract a partir del id del mecanico
     * 
     * @param id
     * @return
     */
    public List<String> findByMechanicId(String id);

    /**
     * Busca contract a partir del name de professionalGroup
     * 
     * @param name
     * @return
     */
    public List<String> findByProfessionalGroupName(String name);

    /**
     * Busca contract (terminated o in force) de un mes
     * 
     * @param date
     * @return
     */
    public List<String> findContractsInMonth(LocalDate date);

    /// FOTOCOPIA DE LA BASE DE DATOS
    public class ContractRecord {
	public String id;
	public double annualbasesalary;
	public LocalDateTime createdAt;
	public Date enddate;
	public String entityState;
	public double settlement;
	public Date startdate;
	public String state;
	public double taxrate;
	public LocalDateTime updatedAt;
	public long version;

	public String contracttype_id;
	public String mechanic_id;
	public String professionalgroup_id;
    }

}
