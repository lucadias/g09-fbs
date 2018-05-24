package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.SortingType;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.exception.LockCheckFailedException;
import ch.hslu.appe.fbs.remote.exception.OrderedArticleNotUpdatedException;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;

import javax.persistence.RollbackException;
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
     * @param sessionId session id to gain access
     * @param id database id of the order
     * @return OrderDTO with the given id
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    OrderDTO getById(String sessionId, int id) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns all orders.
     * @param sessionId session id to gain access
     * @return list with all orders
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<OrderDTO> getList(String sessionId) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns all orders of a client by his id.
     * @param sessionId session id to gain access
     * @param id id of the client
     * @return list with all orders of the client
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<OrderDTO> getListByClientId(String sessionId, int id) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns a list of orders which are matching the search string.
     * @param sessionId session id to gain access
     * @param searchString search string for the search query
     * @return list of matching orders
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<OrderDTO> search(String sessionId, String searchString) throws RemoteException, UserNotLoggedInException;

    /**
     * Saves the order and returns the saved order.
     * @param sessionId session id to gain access
     * @param orderDTO order to save
     * @param hash lock hash of the order
     * @return saved order dto
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     * @throws LockCheckFailedException is thrown if the lock check has failed
     * @throws RollbackException is thrown if there was an error in the entity manager
     * @throws OrderedArticleNotUpdatedException is thrown if any OrderedArticle couldn't get updated
     */
    OrderDTO save(String sessionId, OrderDTO orderDTO, String hash) throws RemoteException, UserNotLoggedInException, LockCheckFailedException, RollbackException, OrderedArticleNotUpdatedException;

    /**
     * Deletes the order.
     * @param sessionId session id to gain access
     * @param orderDTO order to delete
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    FBSFeedback delete(String sessionId, OrderDTO orderDTO, String hash) throws RemoteException, UserNotLoggedInException;

    /**
     * Tries to gain the lock of an order.
     * @param sessionId session id to gain access
     * @param orderDTO order to gain the lock
     * @return lock hash string on success, null on failure
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    String lock(String sessionId, OrderDTO orderDTO) throws RemoteException, UserNotLoggedInException;

    /**
     * Releases the lock of an order.
     * @param sessionId session id to gain access
     * @param orderDTO order to release the lock
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    FBSFeedback release(String sessionId, OrderDTO orderDTO, String hash) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns all orders as a sorted list.
     * @param sessionId session id to gain access
     * @param type how the list has to be sorted
     * @return sorted list of orders
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<OrderDTO> sortList(String sessionId, SortingType type) throws RemoteException, UserNotLoggedInException;

    /**
     * Sorts a given list and returns it.
     * @param sessionId session id to gain access
     * @param orderDTOs list to sort
     * @param type how the list has to be sorted
     * @return sorted list of orders
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<OrderDTO> sortList(String sessionId, List<OrderDTO> orderDTOs, SortingType type) throws RemoteException, UserNotLoggedInException;
}
