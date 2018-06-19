package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.model.entities.Client;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientPersistorIT {

    private ClientPersistor persistor = new ClientPersistor();
    private Client client;

    @Before
    public void setup(){

    }

    @Test
    public void testGetClientFirstname() {

        assertEquals("Bruce",persistor.getById(1).getFirstname());
    }

    @Test
    public void testGetByID() {

        assertEquals(1,persistor.getById(1).getIdClients());
    }

    @Test
    public void testGetClientSurname() {

        assertEquals("Banner",persistor.getById(1).getSurname());
    }

    @Test
    public void testGetListRegex(){
        assertEquals("Parker",persistor.getList("Parker").get(0).getSurname());
    }

    @Test
    public void testGetList(){

        assertEquals("Banner",persistor.getList().get(0).getSurname());
        assertEquals("Parker",persistor.getList().get(1).getSurname());
        assertEquals("Rogers",persistor.getList().get(2).getSurname());
     }

    @Test
    public void testGetClientAddress() {

        assertEquals("Avengerweg 3",persistor.getById(1).getAddress());
    }
}
