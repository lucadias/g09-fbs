package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.Client;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;

/**
 * Converter for client entity and DTO.
 *
 * @author Mischa Gruber
 */
public final class ClientConverter {

    /**
     * Converts a client entity into a DTO.
     * @param client client to be converted
     * @return converted client
     */
    public ClientDTO convertToDTO(final Client client) {
        ClientDTO clientDTO = new ClientDTO(client.getIdClients());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setFirstname(client.getFirstname());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setActive(client.getActive());

        return clientDTO;
    }

    /**
     * Converts a client DTO into an entity.
     * @param clientDTO client to be converted
     * @return converted client
     */
    public Client convertToEntity(final ClientDTO clientDTO) {
        Client client = new Client();
        client.setIdClients(clientDTO.getId());
        client.setSurname(clientDTO.getSurname());
        client.setFirstname(clientDTO.getFirstname());
        client.setAddress(clientDTO.getAddress());
        client.setActive(clientDTO.isActive());

        return client;
    }
}
