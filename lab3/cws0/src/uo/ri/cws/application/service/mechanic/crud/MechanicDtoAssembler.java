package uo.ri.cws.application.service.mechanic.crud;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicDtoAssembler {

    /**
     * Convierte un MechanicRecord en un MechanicDto
     * 
     * @param mr
     * @return
     */
    public static MechanicDto toDto(MechanicRecord mr) {
	MechanicDto dto = new MechanicDto();
	dto.id = mr.id;
	dto.version = mr.version;
	dto.nif = mr.nif;
	dto.name = mr.name;
	dto.surname = mr.surname;

	return dto;
    }

    /**
     * Convierte una lista de MechanicRecords en una lista de MechanicDto
     * 
     * @param records
     * @return
     */
    public static List<MechanicDto> toDoList(List<MechanicRecord> records) {
	return records.stream().map(MechanicDtoAssembler::toDto).toList();
    }

    /**
     * Convierte un MechanicDto en un MechanicRecord
     * 
     * @param dto
     * @return
     */
    public static MechanicRecord toRecord(MechanicDto dto) {
	MechanicRecord mr = new MechanicRecord();
	mr.id = dto.id;
	mr.version = dto.version;
	mr.nif = dto.nif;
	mr.name = dto.name;
	mr.surname = dto.surname;

	/// Campos que no existen en el dto
	mr.createdAt = LocalDateTime.now();
	mr.updatedAt = LocalDateTime.now();
	mr.entityState = "ENABLED"; /// PREGUNTAR: ¿Que entityState tengo que
				    /// poner?

	return mr;
    }

    /**
     * Convierte una lista de MechanicDto en una lista de MechanicRecord
     * 
     * @param records
     * @return
     */
    public static List<MechanicRecord> toRecordList(List<MechanicDto> records) {
	return records.stream().map(MechanicDtoAssembler::toRecord).toList();
    }

    /**
     * Convierte un Optional<MechanicRecord> en un Optional<MechanicDto>
     * 
     * @param maybeRecord
     * @return
     */
    public static Optional<MechanicDto> toDtoOptional(
	Optional<MechanicRecord> maybeRecord) {
	return maybeRecord.map(MechanicDtoAssembler::toDto);
    }

}
