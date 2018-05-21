package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.UserNotLoggedInException;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class LogManager {

    private static LogManager instance = null;

    private static Object mutex = new Object();

    private SessionManager sessionManager;

    public static LogManager getInstance() {
        LogManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new LogManager();
                }
            }
        }
        return result;
    }

    private LogManager() {
        this.sessionManager = SessionManager.getInstance();
    }

    public List<String> getLogList(final String sessionId) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return new ArrayList<>();
        }
        throw new UserNotLoggedInException();
    }
}
