package ch.hslu.appe.fbs.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public interface RemoteLoginService extends Remote {

    String login(String username, String passwordHash) throws RemoteException;

    FBSFeedback logout(String sessionId, String username) throws RemoteException;
}
