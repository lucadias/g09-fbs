package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.EmployeeManager;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteEmployeeService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class EmployeeService extends UnicastRemoteObject implements RemoteEmployeeService {

    public static final long serialVersionUID = 1L;
    private transient EmployeeManager employeeManager;

    public EmployeeService() throws RemoteException {super();

        employeeManager = EmployeeManager.getInstance();
    }

    @Override
    public EmployeeDTO getById(final String sessionId, final int id) throws RemoteException, UserNotLoggedInException {
        return employeeManager.getById(sessionId, id);
    }

    @Override
    public List<EmployeeDTO> getList(final String sessionId) throws RemoteException, UserNotLoggedInException {
        return employeeManager.getList(sessionId);
    }
}
