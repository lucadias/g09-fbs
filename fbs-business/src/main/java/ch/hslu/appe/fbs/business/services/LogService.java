package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.LogManager;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteLogService;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Server implementation of the RemoteLogService interface.
 *
 * @author Mischa Gruber
 */
public final class LogService extends UnicastRemoteObject implements RemoteLogService {

    public static final long serialVersionUID = 1L;
    private transient LogManager logManager;

    /**
     * Constructor of the LogService.
     * @throws RemoteException mandatory
     */
    public LogService() throws RemoteException {
        super();
        this.logManager = LogManager.getInstance();
    }

    @Override
    public List<String> getLogList(final String sessionId) throws IOException, UserNotLoggedInException {
        return logManager.getLogList(sessionId);
    }
}
