package uo.ri.cws.application.service.professionalGroup.crud;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;

public class ProfessionalGroupDtoAssembler {

    /**
     * Convierte un ProfessionalGroupRecord en un ProfessionalGroupDto
     * 
     * @param record
     * @return
     */
    public static ProfessionalGroupDto toDto(ProfessionalGroupRecord record) {
	ProfessionalGroupDto dto = new ProfessionalGroupDto();
	dto.id = record.id;
	dto.version = record.version;
	dto.name = record.name;
	dto.trienniumPayment = record.trienniumPayment;
	dto.productivityRate = record.productivityRate;
	return dto;
    }

    /**
     * Convierte una lista de ProfessionalGroupRecord en una lista de
     * ProfessionalGroupDto
     * 
     * @param records
     * @return
     */
    public static List<ProfessionalGroupDto> toDtoList(
	List<ProfessionalGroupRecord> records) {
	return records.stream()
	    .map(ProfessionalGroupDtoAssembler::toDto)
	    .toList();
    }

    /**
     * Convierte un ProfessionalGroupDto en un ProfessionalGroupRecord
     * 
     * @param dto
     * @return
     */
    public static ProfessionalGroupRecord toRecord(ProfessionalGroupDto dto) {
	ProfessionalGroupRecord record = new ProfessionalGroupRecord();
	record.id = dto.id;
	record.version = dto.version;
	record.name = dto.name;
	record.trienniumPayment = dto.trienniumPayment;
	record.productivityRate = dto.productivityRate;

	// Campos adicionales que no están en el DTO
	record.createdAt = LocalDateTime.now();
	record.updatedAt = LocalDateTime.now();
	record.entityState = "ENABLED";

	return record;
    }

    /**
     * Convierte un Optional ProfessionalGroupRecord en un Optional
     * ProfessionlaGroupDto
     * 
     * @param maybeRecord
     * @return
     */
    public static Optional<ProfessionalGroupDto> toDtoOptional(
	Optional<ProfessionalGroupRecord> maybeRecord) {
	return maybeRecord.map(ProfessionalGroupDtoAssembler::toDto);
    }
}
