package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the order state service.
 *
 * @author Mischa Gruber
 */
public interface RemoteOrderStateService extends Remote {

    /**
     * Returns a OrderStateDTO object with the given id.
     * @param sessionId session id to gain access
     * @param id database id of the order state
     * @return OrderStateDTO with the given id
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    OrderStateDTO getById(String sessionId, int id) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns all order states.
     * @param sessionId session id to gain access
     * @return list with all order states
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<OrderStateDTO> getList(String sessionId) throws RemoteException, UserNotLoggedInException;
}
