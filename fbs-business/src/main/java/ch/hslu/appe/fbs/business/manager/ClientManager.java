package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.ClientConverter;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.data.ClientPersistor;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class ClientManager {
    private static ClientManager instance = null;

    private static Object mutex = new Object();

    private ClientPersistor clientPersistor;
    private ClientConverter clientConverter;

    private SessionManager sessionManager;

    static final Logger logger = LogManager.getLogger(ClientManager.class.getName());


    /**
     * Returns the singleton instance of the ClientManager.
     * @return single instance
     */
    public static ClientManager getInstance() {
        ClientManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new ClientManager();
                }
            }
        }
        return result;
    }

    /**
     * Private constructor for the single pattern.
     */
    private ClientManager() {
        this.clientPersistor = new ClientPersistor();
        this.clientConverter = new ClientConverter();
        this.sessionManager = SessionManager.getInstance();
    }

    public ClientDTO getById(final String sessionId, final int id) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return clientConverter.convertToDTO(clientPersistor.getById(id));
        }
        throw new UserNotLoggedInException();
    }

    public List<ClientDTO> getList(final String sessionId) throws UserNotLoggedInException  {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return clientConverter.convertToDTO(clientPersistor.getList());
        }
        throw new UserNotLoggedInException();
    }
}
