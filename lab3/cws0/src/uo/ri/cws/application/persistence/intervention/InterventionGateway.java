package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

    /**
     * findByMechanicId: Busca intervenciones por el id del mecanico
     * 
     * @param id String: Id del mecanico
     * @return List String
     * 
     *         Ejemplo de uso: List<String> lista =
     *         interventionGateway.findByMechanicId("mechanicId");
     */
    public List<String> findByMechanicId(String id);

    /// FOTOCOPIA DE LA BASE DE DATOS
    public class InterventionRecord {
	public String id;
	public LocalDateTime createdAt;
	public LocalDateTime date;
	public String entityState;
	public int minutes;
	public LocalDateTime updatedAt;
	public long version;

	public String mechanic_id;
	public String workorder_id;

    }

}
