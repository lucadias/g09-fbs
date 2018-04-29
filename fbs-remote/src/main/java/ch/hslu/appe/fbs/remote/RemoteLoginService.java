package ch.hslu.appe.fbs.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for the login service.
 *
 * @author Mischa Gruber
 */
public interface RemoteLoginService extends Remote {

    /**
     * Logs in a user and creates a new session id.
     * @param username username of the user
     * @param passwordHash sha-256 hashed password of the user
     * @return session id string on success, null on failure
     * @throws RemoteException mandatory
     */
    String login(String username, String passwordHash) throws RemoteException;

    /**
     * Logs out a user and removes it's session id.
     * Validates with additional username.
     * @param sessionId session id of the user
     * @param username username for the session id
     * @return FBSFeedback.SUCCESS on success, FBSFeedback.SESSION_ID_USERNAME_NOT_MATCHING or FBSFeedback.SESSION_ID_NOT_EXISTING on failure
     * @throws RemoteException mandatory
     */
    FBSFeedback logout(String sessionId, String username) throws RemoteException;
}
