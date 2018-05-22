package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.GroupConverter;
import ch.hslu.appe.fbs.business.utils.UserNotLoggedInException;
import ch.hslu.appe.fbs.data.EmployeeGroupsPersistor;
import ch.hslu.appe.fbs.data.GroupsPersistor;
import ch.hslu.appe.fbs.model.entities.EmployeeGroups;
import ch.hslu.appe.fbs.model.entities.Groups;
import ch.hslu.appe.fbs.remote.dtos.GroupDTO;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class PermissionManager {

    private static PermissionManager instance = null;

    private static Object mutex = new Object();

    private EmployeeGroupsPersistor employeeGroupsPersistor;
    private GroupsPersistor groupsPersistor;
    private GroupConverter groupConverter;

    private SessionManager sessionManager;

    public static PermissionManager getInstance() {
        PermissionManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new PermissionManager();
                }
            }
        }
        return result;
    }

    private PermissionManager() {
        this.employeeGroupsPersistor = new EmployeeGroupsPersistor();
        this.groupsPersistor = new GroupsPersistor();
        this.groupConverter = new GroupConverter();
        this.sessionManager = SessionManager.getInstance();
    }

    public List<GroupDTO> getGroupsOfEmployee(final String sessionId, final int employeeId) throws RemoteException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            List<EmployeeGroups> employeeGroupsList = employeeGroupsPersistor.getEmployeeGroupsByEmployeeId(employeeId);
            List<Groups> groups = new ArrayList<>();

            for(EmployeeGroups employeeGroup : employeeGroupsList) {
                groups.add(groupsPersistor.getById(employeeGroup.getGroupsIdGroups()));
            }

            return groupConverter.convertToDTO(groups);
        }
        throw new UserNotLoggedInException();
    }
}
