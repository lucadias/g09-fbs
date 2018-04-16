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

    OrderDTO getById(int id) throws RemoteException;

    List<OrderDTO> getList() throws RemoteException;

    List<OrderDTO> getListByClientId(int id) throws RemoteException;

    List<OrderDTO> search(String regEx) throws RemoteException;

    FBSFeedback save(OrderDTO orderDTO, String hash) throws RemoteException;

    FBSFeedback delete(OrderDTO orderDTO, String hash) throws RemoteException;

    String lock(OrderDTO orderDTO) throws RemoteException;

    FBSFeedback release(OrderDTO orderDTO, String hash) throws RemoteException;

    List<OrderDTO> sortList(SortingType type) throws RemoteException;

    List<OrderDTO> sortList(List<OrderDTO> orderDTOs, SortingType type) throws RemoteException;
}
