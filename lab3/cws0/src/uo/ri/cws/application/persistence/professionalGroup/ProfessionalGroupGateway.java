package uo.ri.cws.application.persistence.professionalGroup;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public interface ProfessionalGroupGateway
    extends Gateway<ProfessionalGroupRecord> {

    /**
     * findByName: Busca el professionalGroup por el nombre dado
     * 
     * @param name String: Nombre del professionalGroup
     * @return Optional ProfessionalGroupRecord
     * 
     *         Ejemplo de uso: Optional<ProfessionalGroupRecord> o =
     *         professionalgroupGateway.findByName("groupName")
     */
    public Optional<ProfessionalGroupRecord> findByName(String name);

    public class ProfessionalGroupRecord {
	public String id;
	public LocalDateTime createdAt;
	public String entityState;
	public String name;
	public double productivityRate;
	public double trienniumPayment;
	public LocalDateTime updatedAt;
	public long version;
    }
}
