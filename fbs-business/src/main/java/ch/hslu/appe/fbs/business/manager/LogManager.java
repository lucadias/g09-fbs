package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager for logs as a singleton.
 *
 * @author Mischa Gruber
 */
public final class LogManager {

    private static LogManager instance = null;

    private static final Object mutex = new Object();

    private SessionManager sessionManager;

    /**
     * Returns the singleton instance of the LogManager.
     * @return single instance
     */
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

    /**
     * Private constructor for the single pattern.
     */
    private LogManager() {
        this.sessionManager = SessionManager.getInstance();
    }

    /**
     * Returns a list of logs.
     * @param sessionId session id to gain access
     * @return a list of log entries
     * @throws IOException is thrown if there was an error with accessing the log file
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<String> getLogList(final String sessionId) throws IOException, UserNotLoggedInException {

        List<String> logs = new ArrayList<>();
        if (sessionManager.getIsLoggedIn(sessionId)) {
            try (BufferedReader br = new BufferedReader(new FileReader("FBSLogs.log"))) {
                for (String line;(line = br.readLine()) != null;) {
                    logs.add(line);
                    System.out.println(line); // process the line.
                }
                // line is not visible here.
            }
            return logs;
        }
        throw new UserNotLoggedInException();
    }
}
