package uo.ri.cws.application.persistence.contract;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {

    /**
     * findByMechanicId: Busca contract a partir del id del mecanico
     * 
     * @param id String: Id del mecanico
     * @return List String
     * 
     *         Ejemplo de uso: List<String> lista =
     *         contractGateway.findByMechanicId("mechanicId");
     */
    public List<String> findByMechanicId(String id);

    /**
     * findByProfessionalGroupId: Busca contract a partir del name de
     * professionalGroup
     * 
     * @param name String: Nombre del grupo profesional
     * @return List String
     * 
     *         Ejemplo de uso: List<String> plist =
     *         contractGateway.findByProfessionalGroupId("id");
     */
    public List<String> findByProfessionalGroupId(String id);

    /**
     * findContractsInMonth: Busca contract (terminated o in force) de un mes
     * 
     * @param date LocalDate: fecha
     * @return List String
     * 
     *         Ejemplo de uso: List<String> lista = contractGateway
     *         .findContractsInMonth(date);
     */
    public List<String> findContractsInMonth(LocalDate date);

    /**
     * findInForceContracts: Busca todos los contracts que esten IN FORCE
     * 
     * @return List ContractRecord
     * 
     *         Ejemplo de uso: List<ContractRecord> lista =
     *         contractGateway.findInForceContracts()
     */
    public List<ContractRecord> findInForceContracts();

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
