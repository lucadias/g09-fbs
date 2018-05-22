package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.EmployeeConverter;
import ch.hslu.appe.fbs.business.utils.UserNotLoggedInException;
import ch.hslu.appe.fbs.data.EmployeePersistor;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;

import java.rmi.RemoteException;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class EmployeeManager {
    private static EmployeeManager instance = null;

    private static Object mutex = new Object();

    private EmployeePersistor employeePersistor;
    private EmployeeConverter employeeConverter;

    private SessionManager sessionManager;

    /**
     * Returns the singleton instance of the EmployeeManager.
     * @return single instance
     */
    public static EmployeeManager getInstance() {
        EmployeeManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new EmployeeManager();
                }
            }
        }
        return result;
    }

    /**
     * Private constructor for the single pattern.
     */
    private EmployeeManager() {
        this.employeePersistor = new EmployeePersistor();
        this.employeeConverter = new EmployeeConverter();
        this.sessionManager = SessionManager.getInstance();
    }

    public EmployeeDTO getById(final String sessionId, final int id) throws RemoteException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return employeeConverter.convertToDTO(employeePersistor.getById(id));
        }
        throw new UserNotLoggedInException();
    }

    public List<EmployeeDTO> getList(final String sessionId) throws RemoteException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return employeeConverter.convertToDTO(employeePersistor.getList());
        }
        throw new UserNotLoggedInException();
    }
}
