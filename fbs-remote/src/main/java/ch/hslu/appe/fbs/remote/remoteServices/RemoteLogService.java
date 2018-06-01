package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the log service.
 *
 * @author Mischa Gruber
 */
public interface RemoteLogService extends Remote {

    /**
     * Returns a list of logs.
     * @param sessionId session id to gain access
     * @return a list of log entries
     * @throws RemoteException mandatory
     * @throws IOException is thrown if there was an error with accessing the log file
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<String> getLogList(String sessionId) throws RemoteException, IOException, UserNotLoggedInException;
}
