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
 * Class_Name
 *
 * @author Mischa Gruber
 */
public final class OrderService extends UnicastRemoteObject implements RemoteOrderService {

    private transient OrderManager orderManager;

    public OrderService() throws RemoteException {
        super();

        orderManager = OrderManager.getInstance();
    }

    /**
     * TODO: implement getListByClientId, delete
     */

    @Override
    public OrderDTO getById(final int id) throws RemoteException {
        return orderManager.getById(id);
    }

    @Override
    public List<OrderDTO> getList() throws RemoteException {
        return orderManager.getList();
    }

    @Override
    public List<OrderDTO> getListByClientId(final int id) throws RemoteException {
        return null;
    }

    @Override
    public List<OrderDTO> search(final String regEx) throws RemoteException {
        return orderManager.getList(regEx);
    }

    @Override
    public FBSFeedback save(final OrderDTO orderDTO, final String hash) throws RemoteException {
        return orderManager.save(orderDTO, hash);
    }

    @Override
    public FBSFeedback delete(final OrderDTO orderDTO, final String hash) throws RemoteException {
        return null;
    }

    @Override
    public String lock(final OrderDTO orderDTO) throws RemoteException {
        return orderManager.lock(orderDTO.getId());
    }

    @Override
    public FBSFeedback release(final OrderDTO orderDTO, final String hash) throws RemoteException {
        return orderManager.release(orderDTO.getId(), hash);
    }

    @Override
    public List<OrderDTO> sortList(final SortingType type) throws RemoteException {
        return orderManager.sortList(type);
    }

    @Override
    public List<OrderDTO> sortList(final List<OrderDTO> orderDTOs, final SortingType type) throws RemoteException {
        return orderManager.sortList(orderDTOs, type);
    }
}
