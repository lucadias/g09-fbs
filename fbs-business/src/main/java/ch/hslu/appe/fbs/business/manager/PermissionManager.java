package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.GroupConverter;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.data.EmployeeGroupsPersistor;
import ch.hslu.appe.fbs.data.GroupsPersistor;
import ch.hslu.appe.fbs.model.entities.EmployeeGroups;
import ch.hslu.appe.fbs.model.entities.Groups;
import ch.hslu.appe.fbs.remote.dtos.GroupDTO;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager for permission as a singleton.
 *
 * @author Mischa Gruber
 */
public final class PermissionManager {

    private static PermissionManager instance = null;

    private static final Object MUTEX = new Object();

    private EmployeeGroupsPersistor employeeGroupsPersistor;
    private GroupsPersistor groupsPersistor;
    private GroupConverter groupConverter;

    private SessionManager sessionManager;

    /**
     * Returns the singleton instance of the PermissionManager.
     * @return single instance
     */
    public static PermissionManager getInstance() {
        PermissionManager result = instance;
        if (result == null) {
            synchronized (MUTEX) {
                result = instance;
                if (result == null) {
                    instance = result = new PermissionManager();
                }
            }
        }
        return result;
    }

    /**
     * Private constructor for the single pattern.
     */
    private PermissionManager() {
        this.employeeGroupsPersistor = new EmployeeGroupsPersistor();
        this.groupsPersistor = new GroupsPersistor();
        this.groupConverter = new GroupConverter();
        this.sessionManager = SessionManager.getInstance();
    }

    /**
     * Returns a list of groups, in which the employee is.
     * @param sessionId session id to gain access
     * @param employeeId database id of the employee
     * @return a list of groups of the employee
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<GroupDTO> getGroupsOfEmployee(final String sessionId, final int employeeId)
            throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            List<EmployeeGroups> employeeGroupsList = employeeGroupsPersistor.getEmployeeGroupsByEmployeeId(employeeId);
            List<Groups> groups = new ArrayList<>();

            for (EmployeeGroups employeeGroup : employeeGroupsList) {
                groups.add(groupsPersistor.getById(employeeGroup.getGroupsIdGroups()));
            }

            return groupConverter.convertToDTO(groups);
        }
        throw new UserNotLoggedInException();
    }
}
