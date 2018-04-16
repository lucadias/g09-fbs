package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;

import java.util.List;

/**
 * OrderConverter
 *
 * @author Mischa Gruber
 */
public final class OrderConverter {

    private OrderStateConverter orderStateConverter;
    private OrderedArticleConverter orderedArticleConverter;
    private EmployeeConverter employeeConverter;
    private ClientConverter clientConverter;

    public OrderConverter() {
        this.orderStateConverter = new OrderStateConverter();
        this.orderedArticleConverter = new OrderedArticleConverter();
        this.employeeConverter = new EmployeeConverter();
        this.clientConverter = new ClientConverter();
    }

    public OrderDTO convertToDTO(final Orders order, final OrderStateDTO orderStateDTO, final List<OrderedArticleDTO> orderedArticleDTOs, final EmployeeDTO employeeDTO, final ClientDTO clientDTO) {
        OrderDTO orderDTO = new OrderDTO(order.getIdOrders());
        orderDTO.setDate(order.getDate());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderStateDTO(orderStateDTO);
        orderDTO.setOrderedArticleDTOList(orderedArticleDTOs);
        orderDTO.setEmployeeDTO(employeeDTO);
        orderDTO.setClientDTO(clientDTO);

        return orderDTO;
    }

    public Orders convertToEntity(final OrderDTO orderDTO) {
        Orders order = new Orders();
        order.setIdOrders(orderDTO.getId());
        order.setDate(orderDTO.getDate());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderStateIdOrderState(orderDTO.getOrderStateDTO().getId());
        order.setEmployeeIdEmployee(orderDTO.getEmployeeDTO().getId());

        return order;
    }
}
