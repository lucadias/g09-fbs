package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.OrderManager;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.RemoteOrderService;
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
     * @throws RemoteException
     */
    public OrderService() throws RemoteException {
        super();

        orderManager = OrderManager.getInstance();
    }

    //TODO: implement getListByClientId, delete

    @Override
    public OrderDTO getById(final String sessionId, final int id) throws RemoteException {
        return orderManager.getById(sessionId, id);
    }

    @Override
    public List<OrderDTO> getList(final String sessionId) throws RemoteException {
        return orderManager.getList(sessionId);
    }

    @Override
    public List<OrderDTO> getListByClientId(final String sessionId, final int id) throws RemoteException {
        return null;
    }

    @Override
    public List<OrderDTO> search(final String sessionId, final String regEx) throws RemoteException {
        return orderManager.getList(sessionId, regEx);
    }

    @Override
    public FBSFeedback save(final String sessionId, final OrderDTO orderDTO, final String hash) throws RemoteException {
        return orderManager.save(sessionId, orderDTO, hash);
    }

    @Override
    public FBSFeedback delete(final String sessionId, final OrderDTO orderDTO, final String hash) throws RemoteException {
        return null;
    }

    @Override
    public String lock(final String sessionId, final OrderDTO orderDTO) throws RemoteException {
        return orderManager.lock(sessionId, orderDTO.getId());
    }

    @Override
    public FBSFeedback release(final String sessionId, final OrderDTO orderDTO, final String hash) throws RemoteException {
        return orderManager.release(sessionId, orderDTO.getId(), hash);
    }

    @Override
    public List<OrderDTO> sortList(final String sessionId, final SortingType type) throws RemoteException {
        return orderManager.sortList(sessionId, type);
    }

    @Override
    public List<OrderDTO> sortList(final String sessionId, final List<OrderDTO> orderDTOs, final SortingType type) throws RemoteException {
        return orderManager.sortList(sessionId, orderDTOs, type);
    }
}
