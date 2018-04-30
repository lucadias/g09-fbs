package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.SessionIdGenerator;
import ch.hslu.appe.fbs.data.EmployeePersistor;
import ch.hslu.appe.fbs.model.entities.Employee;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import java.security.MessageDigest;
import java.util.HashMap;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class SessionManager {

    private static SessionManager instance = null;
    private static Object mutex = new Object();

    private HashMap<String, Integer> sessionPool;

    private EmployeePersistor employeePersistor;

    public static SessionManager getInstance() {
        SessionManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new SessionManager();
                }
            }
        }
        return result;
    }

    private SessionManager() {
        sessionPool = new HashMap<>();

        this.employeePersistor = new EmployeePersistor();
    }

    private String createNewSessionId(int employeeId) {
        String sessionId = SessionIdGenerator.getNewId();
        Integer employeeIdInteger = new Integer(employeeId);

        sessionPool.put(sessionId, employeeIdInteger);

        return sessionId;
    }

    public String login(String username, String passwordHash) {
        //Employee employee = employeePersistor.getByUserName(username);
        Employee employee = employeePersistor.getById(1);

        if (employee != null) {
            if (employee.getPassword().equals(passwordHash)) {
                return createNewSessionId(employee.getIdEmployees());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public FBSFeedback logout(String sessionId, String username) {
        //Employee employee = employeePersistor.getByUserName(username);
        Employee employee = employeePersistor.getById(1);
        Integer userId = new Integer(employee.getIdEmployees());

        synchronized (sessionPool) {
            if(sessionPool.containsKey(sessionId)) {
                if(sessionPool.get(sessionId).equals(userId)) {
                    sessionPool.remove(sessionId);
                    return FBSFeedback.SUCCESS;
                } else {
                    return FBSFeedback.SESSION_ID_USERNAME_NOT_MATCHING;
                }
            } else {
                return FBSFeedback.SESSION_ID_NOT_EXISTING;
            }
        }
    }
}
