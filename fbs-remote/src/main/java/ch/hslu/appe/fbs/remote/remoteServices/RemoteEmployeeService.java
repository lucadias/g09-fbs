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

    /**
     * Returns an EmployeeDTO object with the given id.
     * @param sessionId session id to gain access
     * @param id database id of the employee
     * @return an employee dto with the given id
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    EmployeeDTO getById(String sessionId, int id) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns an EmployeeDTO object with the givern username.
     * @param sessionId session id to gain access
     * @param username username of the employee
     * @return and employee dto with the given username
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    EmployeeDTO getByUsername(String sessionId, String username) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns all employees.
     * @param sessionId session id to gain access
     * @return list with all employees
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<EmployeeDTO> getList(String sessionId) throws RemoteException, UserNotLoggedInException;
}
