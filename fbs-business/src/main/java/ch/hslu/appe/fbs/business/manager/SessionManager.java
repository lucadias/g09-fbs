package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.SessionIdGenerator;
import ch.hslu.appe.fbs.data.EmployeePersistor;
import ch.hslu.appe.fbs.model.entities.Employee;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * Manager for sessions as a singleton.
 *
 * @author Mischa Gruber
 */
public final class SessionManager {

    private static SessionManager instance = null;
    private static final Object mutex = new Object();

    private HashMap<String, Integer> sessionPool;

    private EmployeePersistor employeePersistor;

    private static final Logger LOGGER = LogManager.getLogger(SessionManager.class.getName());


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
    private String createNewSessionId(final int employeeId) {
        String sessionId = SessionIdGenerator.getNewId();
        Integer employeeIdInteger = Integer.valueOf(employeeId);

        synchronized (sessionPool) {
            LOGGER.info("Create new Session for Employee with ID: "  + employeeId);
            sessionPool.put(sessionId, employeeIdInteger);
        }

        return sessionId;
    }

    /**
     * Returns the employee id of the given session id.
     * @param sessionId the session id that should be converted
     * @return employee id, which is assigned to the session id
     */
    public int getEmployeeIdFromSessionId(final String sessionId) {
        synchronized (sessionPool) {
            if(sessionPool.containsKey(sessionId)) {
                return sessionPool.get(sessionId);
            }
        }

        return -1;
    }

    /**
     * Returns if the session id is logged in.
     * @param sessionId the session id that has to be checked.
     * @return true if the session id is logged id, otherwise false
     */
    public boolean getIsLoggedIn(final String sessionId) {
        synchronized (sessionPool) {
            if (sessionPool.containsKey(sessionId)) {
                //LOGGER.info("Check if Employee with ID: "  +
                // this.getEmployeeIdFromSessionId(sessionId)+ " is logged in.");
                return true;
            }
        }
        return false;
    }

    /**
     * Validates username and passwordHash for an employee and creates a session id if login is valid.
     * @param username username of the employee
     * @param passwordHash password as a sha-256 hash of the employee
     * @return session id string on success, null on failure
     */
    public String login(final String username, final String passwordHash) {
        System.out.println("employee: " + username);
        Employee employee = employeePersistor.getByUserName(username);
        if (employee != null) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(passwordHash)) {
                LOGGER.info("Login attempt successful Employee: " + username);
                return createNewSessionId(employee.getIdEmployees());
            }
        }
        LOGGER.info("Login attempt unsuccessful Employee: " + username);
        return null;
    }

    /**
     * Logs out a user by removing the session id from the session pool.
     * @param sessionId session id string
     * @param username username of the session id
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     */
    public FBSFeedback logout(final String sessionId, final String username) {
        Employee employee = employeePersistor.getByUserName(username);
        Integer userId = Integer.valueOf(employee.getIdEmployees());

        synchronized (sessionPool) {
            if (sessionPool.containsKey(sessionId)) {
                if (sessionPool.get(sessionId).equals(userId)) {
                    sessionPool.remove(sessionId);
                    LOGGER.info("Logout attempt successful Employee: " + username);
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
