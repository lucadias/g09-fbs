package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.ClientManager;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteClientService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Server implementation of the RemoteClientService interface.
 *
 * @author Mischa Gruber
 */
public final class ClientService extends UnicastRemoteObject implements RemoteClientService {

    public static final long serialVersionUID = 1L;
    private transient ClientManager clientManager;

    /**
     * Constructor of the ClientService.
     * @throws RemoteException mandatory
     */
    public ClientService() throws RemoteException {
        super();
        clientManager = ClientManager.getInstance();
    }

    @Override
    public ClientDTO getById(final String sessionId, final int id) throws RemoteException, UserNotLoggedInException {
        return clientManager.getById(sessionId, id);
    }

    @Override
    public List<ClientDTO> getList(final String sessionId) throws RemoteException, UserNotLoggedInException  {
        return clientManager.getList(sessionId);
    }
}
