package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.dtos.GroupDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the permission service.
 *
 * @author Mischa Gruber
 */
public interface RemotePermissionService extends Remote {

    /**
     * Returns a list of groups, in which a employee is.
     * @param sessionId session id to gain access
     * @param employeeId database id of employee
     * @return list of employee's groups
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<GroupDTO> getGroupsOfEmployee(String sessionId, int employeeId) throws RemoteException, UserNotLoggedInException;
}
