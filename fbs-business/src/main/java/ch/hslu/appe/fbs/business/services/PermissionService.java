package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.PermissionManager;
import ch.hslu.appe.fbs.remote.dtos.GroupDTO;
import ch.hslu.appe.fbs.remote.remoteServices.RemotePermissionService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Server implementation of the RemotePermissionService interface.
 *
 * @author Mischa Gruber
 */
public class PermissionService extends UnicastRemoteObject implements RemotePermissionService {

    public static final long serialVersionUID = 1L;
    private transient PermissionManager permissionManager;

    /**
     * Constructor of the ArticleService.
     * @throws RemoteException mandatory
     */
    public PermissionService() throws RemoteException {
        super();

        permissionManager = PermissionManager.getInstance();
    }

    @Override
    public List<GroupDTO> getGroupsOfEmployee(final String sessionId, final int employeeId) throws RemoteException {
        return permissionManager.getGroupsOfEmployee(sessionId, employeeId);
    }
}
