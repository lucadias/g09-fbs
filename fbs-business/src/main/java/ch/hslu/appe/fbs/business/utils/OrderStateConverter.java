package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.OrderState;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;

/**
 * OrderStateConverter
 *
 * @author Mischa Gruber
 */
public final class OrderStateConverter {

    public OrderStateDTO convertToDTO(final OrderState orderState) {
        OrderStateDTO orderStateDTO = new OrderStateDTO(orderState.getIdOrderState());
        orderStateDTO.setState(orderState.getState());

        return orderStateDTO;
    }

    public OrderState convertToEntity(final OrderStateDTO orderStateDTO) {
        OrderState orderState = new OrderState();
        orderState.setIdOrderState(orderStateDTO.getId());
        orderState.setState(orderStateDTO.getState());

        return orderState;
    }
}
