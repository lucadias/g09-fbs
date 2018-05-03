package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.OrderStateConverter;
import ch.hslu.appe.fbs.data.OrderStatePersistor;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;

import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class OrderStateManager {
    private static OrderStateManager instance = null;

    private static Object mutex = new Object();

    private OrderStatePersistor orderStatePersistor;
    private OrderStateConverter orderStateConverter;

    /**
     * Returns the singleton instance of the OrderStateManager.
     * @return single instance
     */
    public static OrderStateManager getInstance() {
        OrderStateManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new OrderStateManager();
                }
            }
        }
        return result;
    }

    /**
     * Private constructor for the single pattern.
     */
    private OrderStateManager() {
        this.orderStatePersistor = new OrderStatePersistor();
        this.orderStateConverter = new OrderStateConverter();
    }

    public OrderStateDTO getById(final String sessionId, final int id) {
        return orderStateConverter.convertToDTO(orderStatePersistor.getById(id));
    }

    public List<OrderStateDTO> getList(final String sessionId) {
        return orderStateConverter.convertToDTO(orderStatePersistor.getList());
    }
}
