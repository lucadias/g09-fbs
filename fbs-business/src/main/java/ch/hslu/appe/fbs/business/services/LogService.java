package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.LogManager;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteLogService;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class LogService extends UnicastRemoteObject implements RemoteLogService {

    public static final long serialVersionUID = 1L;
    private transient LogManager logManager;

    public LogService() throws RemoteException {
        super();

        this.logManager = LogManager.getInstance();
    }

    @Override
    public List<String> getLogList(String sessionId) throws IOException {
        return logManager.getLogList(sessionId);
    }
}
