package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.SessionManager;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteLoginService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Server implementation of the RemoteLoginService interface.
 *
 * @author Mischa Gruber
 */
public final class LoginService extends UnicastRemoteObject implements RemoteLoginService {

    public static final long serialVersionUID = 1L;
    private transient SessionManager sessionManager;

    /**
     * Constructor of the LoginService.
     * @throws RemoteException mandatory
     */
    public LoginService() throws RemoteException {
        super();

        sessionManager = SessionManager.getInstance();
    }

    @Override
    public String login(final String username, final String passwordHash) throws RemoteException {
        return sessionManager.login(username, passwordHash);
    }

    @Override
    public FBSFeedback logout(final String sessionId, final String username) throws RemoteException {
        return sessionManager.logout(sessionId, username);
    }
}
