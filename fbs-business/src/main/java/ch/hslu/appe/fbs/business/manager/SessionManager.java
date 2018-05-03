package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.SessionIdGenerator;
import ch.hslu.appe.fbs.data.EmployeePersistor;
import ch.hslu.appe.fbs.model.entities.Employee;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

/**
 * Manager for sessions as a singleton.
 *
 * @author Mischa Gruber
 */
public class SessionManager {

    private static SessionManager instance = null;
    private static Object mutex = new Object();

    private HashMap<String, Integer> sessionPool;

    private EmployeePersistor employeePersistor;

    /**
     * Returns the singleton instance of the SessionManager.
     * @return single instance
     */
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

    /**
     * Private constructor for the singleton pattern.
     */
    private SessionManager() {
        sessionPool = new HashMap<>();

        this.employeePersistor = new EmployeePersistor();
    }

    /**
     * Generates a new session id and saves it with the employeeId in the session pool.
     * @param employeeId database id of the employee
     * @return new created session id
     */
    private String createNewSessionId(int employeeId) {
        String sessionId = SessionIdGenerator.getNewId();
        Integer employeeIdInteger = new Integer(employeeId);

        synchronized (sessionPool) {
            sessionPool.put(sessionId, employeeIdInteger);
        }

        return sessionId;
    }

    /**
     * Validates username and passwordHash for an employee and creates a session id if login is valid.
     * @param username username of the employee
     * @param passwordHash password as a sha-256 hash of the employee
     * @return session id string on success, null on failure
     */
    public String login(String username, String passwordHash) {
        System.out.println("employee: " + username);
        Employee employee = employeePersistor.getByUserName(username);
        if (employee != null) {
            if (employee.getUsername().equals(username)) {
                System.out.println("database: " + employee.getPassword());
                System.out.println("client: " + passwordHash);
                
                if(employee.getPassword().equals(passwordHash)) {
                    return createNewSessionId(employee.getIdEmployees());
                }
            }
        }

        /*List<Employee> employees = employeePersistor.getList();
        for(Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                if(employee.getPassword().equals(passwordHash)) {
                    return createNewSessionId(employee.getIdEmployees());
                }
            }
        }*/
        return null;
    }

    /**
     * Logs out a user by removing the session id from the session pool.
     * @param sessionId session id string
     * @param username username of the session id
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     */
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
