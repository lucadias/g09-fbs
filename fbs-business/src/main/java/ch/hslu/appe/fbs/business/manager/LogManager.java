package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.UserNotLoggedInException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public List<String> getLogList(final String sessionId) throws IOException {

        List<String> logs = new ArrayList<>();
        if (sessionManager.getIsLoggedIn(sessionId)) {
            try(BufferedReader br = new BufferedReader(new FileReader("FBSLogs.log"))) {
                for(String line; (line = br.readLine()) != null; ) {
                    logs.add(line);
                    System.out.println(line);// process the line.
                }
                // line is not visible here.
            }
            return logs;
        }
        throw new UserNotLoggedInException();
    }
}
