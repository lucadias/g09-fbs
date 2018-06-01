package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.EmployeeConverter;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.data.EmployeePersistor;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Manager for employees as a singleton.
 *
 * @author Mischa Gruber
 */
public final class EmployeeManager {
    private static EmployeeManager instance = null;

    private static final Object MUTEX = new Object();

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
            synchronized (MUTEX) {
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

    /**
     * Gets the employee by the database id as an entity, converts it to a DTO and returns it.
     * @param sessionId session id to gain access
     * @param id database id of the employee
     * @return employee as a DTO
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public EmployeeDTO getById(final String sessionId, final int id) throws UserNotLoggedInException  {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return employeeConverter.convertToDTO(employeePersistor.getById(id));
        }
        throw new UserNotLoggedInException();
    }

    public EmployeeDTO getByUsername(final String sessionId, final String username) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return employeeConverter.convertToDTO(employeePersistor.getByUserName(username));
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Gets all the employees as entities, converts and returns them as a list.
     * @param sessionId session id to gain access
     * @return employees as a DTO list
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<EmployeeDTO> getList(final String sessionId) throws UserNotLoggedInException  {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return employeeConverter.convertToDTO(employeePersistor.getList());
        }
        throw new UserNotLoggedInException();
    }
}
