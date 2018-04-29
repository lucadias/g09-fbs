package ch.hslu.appe.fbs.remote;

import ch.hslu.appe.fbs.remote.dtos.OrderDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the order service.
 *
 * @author Mischa Gruber
 */
public interface RemoteOrderService extends Remote {

    /**
     * Returns an OrderDTO object with the given id.
     * @param id database id of the order
     * @return OrderDTO with the given id
     * @throws RemoteException mandatory
     */
    OrderDTO getById(int id) throws RemoteException;

    /**
     * Returns all orders.
     * @return list with all orders
     * @throws RemoteException mandatory
     */
    List<OrderDTO> getList() throws RemoteException;

    /**
     * Returns all orders of a client by his id.
     * @param id id of the client
     * @return list with all orders of the client
     * @throws RemoteException mandatory
     */
    List<OrderDTO> getListByClientId(int id) throws RemoteException;

    /**
     * Returns a list of orders which are matching the regular expression.
     * @param regEx regular expression for the search query
     * @return list of matching orders
     * @throws RemoteException mandatory
     */
    List<OrderDTO> search(String regEx) throws RemoteException;

    /**
     * Saves the order.
     * @param orderDTO order to save
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedbacks
     * @throws RemoteException mandatory
     */
    FBSFeedback save(OrderDTO orderDTO, String hash) throws RemoteException;

    /**
     * Deletes the order.
     * @param orderDTO order to delete
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws RemoteException mandatory
     */
    FBSFeedback delete(OrderDTO orderDTO, String hash) throws RemoteException;

    /**
     * Tries to gain the lock of an order.
     * @param orderDTO order to gain the lock
     * @return lock hash string on success, null on failure
     * @throws RemoteException mandatory
     */
    String lock(OrderDTO orderDTO) throws RemoteException;

    /**
     * Releases the lock of an order.
     * @param orderDTO order to release the lock
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws RemoteException mandatory
     */
    FBSFeedback release(OrderDTO orderDTO, String hash) throws RemoteException;

    /**
     * Returns all orders as a sorted list.
     * @param type how the list has to be sorted
     * @return ordered list of orders
     * @throws RemoteException mandatory
     */
    List<OrderDTO> sortList(SortingType type) throws RemoteException;

    /**
     * Sorts a given list and returns it.
     * @param orderDTOs list to sort
     * @param type how the list has to be sorted
     * @return ordered list of orders
     * @throws RemoteException mandatory
     */
    List<OrderDTO> sortList(List<OrderDTO> orderDTOs, SortingType type) throws RemoteException;
}
