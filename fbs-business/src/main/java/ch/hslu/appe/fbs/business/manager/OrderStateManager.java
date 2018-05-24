package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.OrderStateConverter;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.data.OrderStatePersistor;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Manager for order states as a singleton.
 *
 * @author Mischa Gruber
 */
public final class OrderStateManager {
    private static OrderStateManager instance = null;

    private static final Object mutex = new Object();

    private OrderStatePersistor orderStatePersistor;
    private OrderStateConverter orderStateConverter;

    private SessionManager sessionManager;

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
        this.sessionManager = SessionManager.getInstance();
    }

    /**
     * Returns a OrderStateDTO object with the given id.
     * @param sessionId session id to gain access
     * @param id database id of the order state
     * @return OrderStateDTO with the given id
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public OrderStateDTO getById(final String sessionId, final int id) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return orderStateConverter.convertToDTO(orderStatePersistor.getById(id));
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Returns all order states.
     * @param sessionId session id to gain access
     * @return list with all order states
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<OrderStateDTO> getList(final String sessionId) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return orderStateConverter.convertToDTO(orderStatePersistor.getList());
        }
        throw new UserNotLoggedInException();
    }
}
