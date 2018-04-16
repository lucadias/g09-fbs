package ch.hslu.appe.fbs.remote;

import ch.hslu.appe.fbs.remote.dtos.OrderDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Class_Name
 *
 * @author Mischa Gruber
 */
public interface RemoteOrderService extends Remote {

    OrderDTO getById(final int id) throws RemoteException;

    List<OrderDTO> getList() throws RemoteException;

    List<OrderDTO> getListByClientId(final int id) throws RemoteException;

    List<OrderDTO> search(final String regEx) throws RemoteException;

    FBSFeedback save(final OrderDTO orderDTO, String hash) throws RemoteException;

    FBSFeedback delete(final OrderDTO orderDTO, String hash) throws RemoteException;

    String lock(final OrderDTO orderDTO) throws RemoteException;

    FBSFeedback release(final OrderDTO orderDTO, String hash) throws RemoteException;

    List<OrderDTO> sortList(SortingType type) throws RemoteException;

    List<OrderDTO> sortList(List<OrderDTO> orderDTOs, SortingType type) throws RemoteException;
}
