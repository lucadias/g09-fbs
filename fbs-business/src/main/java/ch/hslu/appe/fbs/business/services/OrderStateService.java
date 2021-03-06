package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.OrderStateManager;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteOrderStateService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Server implementation of the RemoteOrderStateService interface.
 *
 * @author Mischa Gruber
 */
public final class OrderStateService extends UnicastRemoteObject implements RemoteOrderStateService {

    public static final long serialVersionUID = 1L;
    private transient OrderStateManager orderStateManager;

    /**
     * Constructor of the OrderStateService.
     * @throws RemoteException mandatory
     */
    public OrderStateService() throws RemoteException {
        super();
        orderStateManager = OrderStateManager.getInstance();
    }

    @Override
    public OrderStateDTO getById(final String sessionId, final int id) throws UserNotLoggedInException {
        return orderStateManager.getById(sessionId, id);
    }

    @Override
    public List<OrderStateDTO> getList(final String sessionId) throws UserNotLoggedInException {
        return orderStateManager.getList(sessionId);
    }
}
