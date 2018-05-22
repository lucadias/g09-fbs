package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;

import java.util.List;

/**
 * Converter for order entity and DTO.
 *
 * @author Mischa Gruber
 */
public final class OrderConverter {

    /**
     * Converts an order entity into a DTO with additional given DTOs.
     * Additional given DTOs will be set and have to be converted before this operation.
     * @param order order to be converted
     * @param orderStateDTO already converted OrderState
     * @param orderedArticleDTOs already converted OrderedArticle list
     * @param employeeDTO already converted Employee
     * @param clientDTO already converted Client
     * @return converted order
     */
    public OrderDTO convertToDTO(final Orders order, final OrderStateDTO orderStateDTO,
                                 final List<OrderedArticleDTO> orderedArticleDTOs,
                                 final EmployeeDTO employeeDTO, final ClientDTO clientDTO) {
        OrderDTO orderDTO = new OrderDTO(order.getIdOrders());
        orderDTO.setDate(order.getDate());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderStateDTO(orderStateDTO);
        orderDTO.setOrderedArticleDTOList(orderedArticleDTOs);
        orderDTO.setEmployeeDTO(employeeDTO);
        orderDTO.setClientDTO(clientDTO);

        return orderDTO;
    }

    /**
     * Converts an order DTO into an entity.
     * Additional DTOs have to be converted separately before or after this operation to save
     * all data informations.
     * @param orderDTO order to be converted
     * @return converted order
     */
    public Orders convertToEntity(final OrderDTO orderDTO) {
        Orders order = new Orders();
        order.setIdOrders(orderDTO.getId());
        order.setDate(orderDTO.getDate());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderStateIdOrderState(orderDTO.getOrderStateDTO().getId());
        order.setEmployeeIdEmployee(orderDTO.getEmployeeDTO().getId());
        order.setClientIdClients(orderDTO.getClientDTO().getId());

        return order;
    }
}
