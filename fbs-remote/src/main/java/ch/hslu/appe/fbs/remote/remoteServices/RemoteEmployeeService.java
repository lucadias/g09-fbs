package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the employee service.
 *
 * @author Mischa Gruber
 */
public interface RemoteEmployeeService extends Remote {

    EmployeeDTO getById(final String sessionId, final int id) throws RemoteException, UserNotLoggedInException;

    List<EmployeeDTO> getList(final String sessionId) throws RemoteException, UserNotLoggedInException;
}
