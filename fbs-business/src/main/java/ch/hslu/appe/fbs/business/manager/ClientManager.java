package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.ClientConverter;
import ch.hslu.appe.fbs.data.ClientPersistor;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;

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
    }

    public ClientDTO getById(final String sessionId, final int id) {
        return clientConverter.convertToDTO(clientPersistor.getById(id));
    }

    public List<ClientDTO> getList(final String sessionId) {
        return clientConverter.convertToDTO(clientPersistor.getList());
    }
}
