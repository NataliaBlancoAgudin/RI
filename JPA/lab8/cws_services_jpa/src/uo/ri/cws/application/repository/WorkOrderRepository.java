package uo.ri.cws.application.repository;

import java.util.List;

import uo.ri.cws.domain.WorkOrder;

public interface WorkOrderRepository extends Repository<WorkOrder> {

    /**
     * @param idsAveria, lista de los id de avería a recuperar
     * @return lista con averias cuyo id aparece en idsAveria, o lista vacía si
     *         no hay ninguna
     */
    List<WorkOrder> findByIds(List<String> workOrderIds);

    /**
     * Devuelve una lista de workorders dado un cliente dado
     * 
     * @param nif Nif del cliente
     * @return List<WorkOrder> Lista de Workorders dado el nif de un cliente
     */
    List<WorkOrder> findWorkOrdersByClientNif(String nif);

    /**
     * Devuelve una lista de workorders pasado un nif de un cliente
     * 
     * @param nif Nif del cliente
     * @return List<WorkOrder>
     */
    List<WorkOrder> findNotInvoicedWorkOrdersByClientNif(String nif);

    /**
     * Devuelve una lista de workoders asociadas a un vehiculo pasado la
     * matricula
     * 
     * @param plate
     * @return
     */
    List<WorkOrder> findWorkOrdersByPlateNumber(String plate);
}