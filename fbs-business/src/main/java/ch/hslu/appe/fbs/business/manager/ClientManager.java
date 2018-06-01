package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.ClientConverter;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.data.ClientPersistor;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Manager for clients as a singleton.
 *
 * @author Mischa Gruber
 */
public final class ClientManager {
    private static ClientManager instance = null;

    private static final Object MUTEX = new Object();

    private ClientPersistor clientPersistor;
    private ClientConverter clientConverter;

    private SessionManager sessionManager;

    static final Logger LOGGER = LogManager.getLogger(ClientManager.class.getName());


    /**
     * Returns the singleton instance of the ClientManager.
     * @return single instance
     */
    public static ClientManager getInstance() {
        ClientManager result = instance;
        if (result == null) {
            synchronized (MUTEX) {
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

    /**
     * Gets the client by the database id as an entity, converts it to a DTO and returns it.
     * @param sessionId session id to gain access
     * @param id database id of the client
     * @return client as a DTO
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public ClientDTO getById(final String sessionId, final int id) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return clientConverter.convertToDTO(clientPersistor.getById(Integer.valueOf(id)));
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Gets all the clients as entities, converts and returns them as a list.
     * @param sessionId session id to gain access
     * @return clients as a DTO list
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<ClientDTO> getList(final String sessionId) throws UserNotLoggedInException  {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return clientConverter.convertToDTO(clientPersistor.getList());
        }
        throw new UserNotLoggedInException();
    }
}
