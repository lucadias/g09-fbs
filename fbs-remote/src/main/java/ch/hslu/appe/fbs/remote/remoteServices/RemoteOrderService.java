package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.SortingType;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;

import javax.persistence.RollbackException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the order service.
 *luca
 * @author Mischa Gruber
 */
public interface RemoteOrderService extends Remote {

    /**
     * Returns an OrderDTO object with the given id.
     * @param sessionId session id to gain access
     * @param id database id of the order
     * @return OrderDTO with the given id
     * @throws RemoteException mandatory
     */
    OrderDTO getById(String sessionId, int id) throws RemoteException;

    /**
     * Returns all orders.
     * @param sessionId session id to gain access
     * @return list with all orders
     * @throws RemoteException mandatory
     */
    List<OrderDTO> getList(String sessionId) throws RemoteException;

    /**
     * Returns all orders of a client by his id.
     * @param sessionId session id to gain access
     * @param id id of the client
     * @return list with all orders of the client
     * @throws RemoteException mandatory
     */
    List<OrderDTO> getListByClientId(String sessionId, int id) throws RemoteException;

    /**
     * Returns a list of orders which are matching the regular expression.
     * @param sessionId session id to gain access
     * @param regEx regular expression for the search query
     * @return list of matching orders
     * @throws RemoteException mandatory
     */
    List<OrderDTO> search(String sessionId, String regEx) throws RemoteException;

    /**
     * Saves the order.
     * @param sessionId session id to gain access
     * @param orderDTO order to save
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws RemoteException mandatory
     */
    FBSFeedback save(String sessionId, OrderDTO orderDTO, String hash) throws RemoteException, RollbackException;

    /**
     * Deletes the order.
     * @param sessionId session id to gain access
     * @param orderDTO order to delete
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws RemoteException mandatory
     */
    FBSFeedback delete(String sessionId, OrderDTO orderDTO, String hash) throws RemoteException;

    /**
     * Tries to gain the lock of an order.
     * @param sessionId session id to gain access
     * @param orderDTO order to gain the lock
     * @return lock hash string on success, null on failure
     * @throws RemoteException mandatory
     */
    String lock(String sessionId, OrderDTO orderDTO) throws RemoteException;

    /**
     * Releases the lock of an order.
     * @param sessionId session id to gain access
     * @param orderDTO order to release the lock
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws RemoteException mandatory
     */
    FBSFeedback release(String sessionId, OrderDTO orderDTO, String hash) throws RemoteException;

    /**
     * Returns all orders as a sorted list.
     * @param sessionId session id to gain access
     * @param type how the list has to be sorted
     * @return sorted list of orders
     * @throws RemoteException mandatory
     */
    List<OrderDTO> sortList(String sessionId, SortingType type) throws RemoteException;

    /**
     * Sorts a given list and returns it.
     * @param sessionId session id to gain access
     * @param orderDTOs list to sort
     * @param type how the list has to be sorted
     * @return sorted list of orders
     * @throws RemoteException mandatory
     */
    List<OrderDTO> sortList(String sessionId, List<OrderDTO> orderDTOs, SortingType type) throws RemoteException;
}
