package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.SessionManager;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.RemoteLoginService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class LoginService extends UnicastRemoteObject implements RemoteLoginService {

    private SessionManager sessionManager;

    public LoginService() throws RemoteException {
        super();

        sessionManager = SessionManager.getInstance();
    }

    @Override
    public String login(String username, String passwordHash) throws RemoteException {
        return sessionManager.login(username, passwordHash);
    }

    @Override
    public FBSFeedback logout(String sessionId, String username) throws RemoteException {
        return sessionManager.logout(sessionId, username);
    }
}
