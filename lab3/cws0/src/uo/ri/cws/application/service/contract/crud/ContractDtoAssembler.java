package uo.ri.cws.application.service.contract.crud;

import java.util.List;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;

public class ContractDtoAssembler {

    /**
     * Convierte un ContractRecord en un ContractDto
     * 
     * @param cr
     * @return
     */
    public static ContractDto toDto(ContractRecord cr) {
	ContractDto dto = new ContractDto();

	dto.id = cr.id;
	dto.version = cr.version;
	dto.mechanic.id = cr.mechanic_id;
	dto.contractType.id = cr.contracttype_id;
	dto.professionalGroup.id = cr.professionalgroup_id;
	dto.startDate = cr.startdate.toLocalDate();
	dto.annualBaseSalary = cr.annualbasesalary;
	dto.taxRate = cr.taxrate;
	dto.settlement = cr.settlement;
	dto.state = cr.state;

	return dto;
    }

    /**
     * Convierte una lista de ContractRecord en una lista de ContractDto
     * 
     * @param records
     * @return
     */
    public static List<ContractDto> toDtoList(List<ContractRecord> records) {
	return records.stream().map(ContractDtoAssembler::toDto).toList();
    }

    /**
     * Convierte un ContractDto a un ContractSummaryDto
     * 
     * @param dto
     * @return
     */
    public static ContractSummaryDto toSummaryDto(ContractDto dto) {
	ContractSummaryDto summary = new ContractSummaryDto();

	summary.id = dto.id;
	summary.nif = dto.mechanic.nif;
	summary.settlement = dto.settlement;
	summary.state = dto.state;

	return summary;
    }

    /**
     * Convierte una lista de ContractDto a una lista de ContractSummaryDto
     * 
     * @param records
     * @return
     */
    public static List<ContractSummaryDto> toSummaryDtoList(
	List<ContractDto> records) {
	return records.stream()
	    .map(ContractDtoAssembler::toSummaryDto)
	    .toList();
    }

}
