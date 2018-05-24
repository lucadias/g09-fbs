package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.OrderManager;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.exception.LockCheckFailedException;
import ch.hslu.appe.fbs.remote.exception.OrderedArticleNotUpdatedException;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteOrderService;
import ch.hslu.appe.fbs.remote.SortingType;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Server implementation of the RemoteOrderService interface.
 *
 * @author Mischa Gruber
 */
public final class OrderService extends UnicastRemoteObject implements RemoteOrderService {

    public static final long serialVersionUID = 1L;
    private transient OrderManager orderManager;

    /**
     * Constructor of the OrderService.
     * @throws RemoteException mandatory
     */
    public OrderService() throws RemoteException {
        super();

        orderManager = OrderManager.getInstance();
    }

    @Override
    public OrderDTO getById(final String sessionId, final int id) throws UserNotLoggedInException {
        return orderManager.getById(sessionId, id);
    }

    @Override
    public List<OrderDTO> getList(final String sessionId) throws UserNotLoggedInException {
        return orderManager.getList(sessionId);
    }

    @Override
    public List<OrderDTO> getListByClientId(final String sessionId, final int id) throws UserNotLoggedInException {
        return orderManager.getListByClientId(sessionId, id);
    }

    @Override
    public List<OrderDTO> search(final String sessionId, final String regEx) throws UserNotLoggedInException {
        return orderManager.getList(sessionId, regEx);
    }

    @Override
    public OrderDTO save(final String sessionId, final OrderDTO orderDTO, final String hash)
            throws UserNotLoggedInException, LockCheckFailedException, OrderedArticleNotUpdatedException {
        return orderManager.save(sessionId, orderDTO, hash);
    }

    @Override
    public FBSFeedback delete(final String sessionId, final OrderDTO orderDTO, final String hash)
            throws UserNotLoggedInException {
        return orderManager.delete(sessionId, orderDTO, hash);
    }

    @Override
    public String lock(final String sessionId, final OrderDTO orderDTO) throws UserNotLoggedInException {
        return orderManager.lock(sessionId, orderDTO.getId());
    }

    @Override
    public FBSFeedback release(final String sessionId, final OrderDTO orderDTO, final String hash)
            throws UserNotLoggedInException {
        return orderManager.release(sessionId, orderDTO.getId(), hash);
    }

    @Override
    public List<OrderDTO> sortList(final String sessionId, final SortingType type) throws UserNotLoggedInException {
        return orderManager.sortList(sessionId, type);
    }

    @Override
    public List<OrderDTO> sortList(final String sessionId, final List<OrderDTO> orderDTOs, final SortingType type)
            throws UserNotLoggedInException {
        return orderManager.sortList(sessionId, orderDTOs, type);
    }
}
