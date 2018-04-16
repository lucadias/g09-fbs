package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.Client;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;

/**
 * ClientConverter
 *
 * @author Mischa Gruber
 */
public final class ClientConverter {

    public ClientDTO convertToDTO(final Client client) {
        ClientDTO clientDTO = new ClientDTO(client.getIdClients());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setFirstname(client.getFirstname());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setActive(client.getActive());

        return clientDTO;
    }

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
