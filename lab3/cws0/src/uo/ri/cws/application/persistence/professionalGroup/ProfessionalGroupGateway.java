package uo.ri.cws.application.persistence.professionalGroup;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public interface ProfessionalGroupGateway
    extends Gateway<ProfessionalGroupRecord> {

    /**
     * Busca el professionalGroup por el nombre dado
     * 
     * @param name
     * @return Optional ProfessionalGroupRecord cuyo nombre sea el pasado como
     *         parametro
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
