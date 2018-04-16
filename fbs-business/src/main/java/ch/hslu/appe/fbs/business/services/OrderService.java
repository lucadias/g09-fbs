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
     * TODO: implement methods
     */

    @Override
    public OrderDTO getById(final int id) throws RemoteException {
        return null;
    }

    @Override
    public List<OrderDTO> getList() throws RemoteException {
        return null;
    }

    @Override
    public List<OrderDTO> getListByClientId(final int id) throws RemoteException {
        return null;
    }

    @Override
    public List<OrderDTO> search(final String regEx) throws RemoteException {
        return null;
    }

    @Override
    public FBSFeedback save(final OrderDTO orderDTO, final String hash) throws RemoteException {
        return null;
    }

    @Override
    public FBSFeedback delete(final OrderDTO orderDTO, final String hash) throws RemoteException {
        return null;
    }

    @Override
    public String lock(final OrderDTO orderDTO) throws RemoteException {
        return null;
    }

    @Override
    public FBSFeedback release(final OrderDTO orderDTO, final String hash) throws RemoteException {
        return null;
    }

    @Override
    public List<OrderDTO> sortList(final SortingType type) throws RemoteException {
        return null;
    }

    @Override
    public List<OrderDTO> sortList(final List<OrderDTO> orderDTOs, final SortingType type) throws RemoteException {
        return null;
    }
}
