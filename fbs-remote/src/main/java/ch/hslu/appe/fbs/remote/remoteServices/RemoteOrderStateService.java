package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the order state service.
 *
 * @author Mischa Gruber
 */
public interface RemoteOrderStateService extends Remote {

    OrderStateDTO getById(final String sessionId, final int id) throws RemoteException;

    List<OrderStateDTO> getList(final String sessionId) throws RemoteException;
}