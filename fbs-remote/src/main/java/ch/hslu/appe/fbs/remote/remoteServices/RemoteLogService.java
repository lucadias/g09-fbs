package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.FBSFeedback;

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
     * @return log list
     * @throws RemoteException mandatory
     */
    List<String> getLogList(String sessionId) throws IOException;
}
