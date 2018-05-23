package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.dtos.ClientDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the client service.
 *
 * @author Mischa Gruber
 */
public interface RemoteClientService extends Remote {

    ClientDTO getById(final String sessionId, final int id) throws RemoteException, UserNotLoggedInException;

    List<ClientDTO> getList(final String sessionId) throws RemoteException, UserNotLoggedInException ;
}
