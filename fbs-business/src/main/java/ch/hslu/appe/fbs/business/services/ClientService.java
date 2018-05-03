package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.ClientManager;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteClientService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class ClientService extends UnicastRemoteObject implements RemoteClientService {

    public static final long serialVersionUID = 1L;
    private transient ClientManager clientManager;

    public ClientService() throws RemoteException {super();

        clientManager = ClientManager.getInstance();
    }

    @Override
    public ClientDTO getById(String sessionId, int id) throws RemoteException {
        return clientManager.getById(sessionId, id);
    }

    @Override
    public List<ClientDTO> getList(String sessionId) throws RemoteException {
        return clientManager.getList(sessionId);
    }
}
