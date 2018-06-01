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

    /**
     * Returns a ClientDTO object with the given id.
     * @param sessionId session id to gain access
     * @param id database id of the client
     * @return a client dto with the given id
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    ClientDTO getById(String sessionId, int id) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns all clients.
     * @param sessionId session id to gain access
     * @return list with all clients
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<ClientDTO> getList(String sessionId) throws RemoteException, UserNotLoggedInException;
}
