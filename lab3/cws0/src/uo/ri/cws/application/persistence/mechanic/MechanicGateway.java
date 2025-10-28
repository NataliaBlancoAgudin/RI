package uo.ri.cws.application.persistence.mechanic;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public interface MechanicGateway extends Gateway<MechanicRecord> {

    /**
     * findByNif: Encuentra el mecanico por el nif
     * 
     * @param nif String: Nif del mecanico
     * @return Optional MechanicRecord
     * 
     *         Ejemplo de uso: Optional<MechanicRecord> om =
     *         mechanicGateway.findByNif("nif");
     */
    public Optional<MechanicRecord> findByNif(String nif);

    /// FOTOCOPIA DE LA BASE DE DATOS
    public class MechanicRecord {
	public String id;
	public long version;
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;
	public String entityState;

	public String nif;
	public String name;
	public String surname;
    }

}
