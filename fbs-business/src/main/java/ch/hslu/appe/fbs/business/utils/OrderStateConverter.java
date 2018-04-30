package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.OrderState;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;

/**
 * Converter for OrderState entity and DTO.
 *
 * @author Mischa Gruber
 */
public final class OrderStateConverter {

    /**
     * Converts an OrderState entity into a DTO.
     * @param orderState OrderState to be converted
     * @return converted OrderState
     */
    public OrderStateDTO convertToDTO(final OrderState orderState) {
        OrderStateDTO orderStateDTO = new OrderStateDTO(orderState.getIdOrderState());
        orderStateDTO.setState(orderState.getState());

        return orderStateDTO;
    }

    /**
     * Converts an OrderState DTO into an entity.
     * @param orderStateDTO OrderState to be converted
     * @return converted OrderState
     */
    public OrderState convertToEntity(final OrderStateDTO orderStateDTO) {
        OrderState orderState = new OrderState();
        orderState.setIdOrderState(orderStateDTO.getId());
        orderState.setState(orderStateDTO.getState());

        return orderState;
    }
}
