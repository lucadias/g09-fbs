package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.model.entities.Client;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Class for persisting Client entities.
 *
 * @author Pascal Stalder
 */
public class ClientPersistor {

    private final EntityManager entitymanager = Util.entityManager;

    /**
     * Returns a Client entity by id.
     *
     * @param id of the wanted Client.
     * @return Client
     */
    public Client getById(int id) {
        Util.transactionBegin();
        Client client = entitymanager.find(Client.class, id);


        //code

        Util.transactionCommit();

        return client;
    }

    public Client getByClientNr(int clientNr) { return this.getById(clientNr);}

    public FBSFeedback save(Client client) { return Util.save(client); }

    /**
     * Returns all Clients
     * @return List&gt;Client&lt;
     */
    public List<Client> getList() {
        List<Client> list = this.entitymanager.createQuery("Select c From Client c").getResultList();
        for (Client client:list){
            if(!client.getActive()){
                list.remove(client);
            }
        }
        return list;
    }

    /**
     * Returns a List of Clients that resemble the searchText.
     * @param searchText String
     * @return List&gt;Client&lt;
     */
    public List<Client> getList(String searchText){
        String regex = "%"+searchText+"%";
        //noinspection JpaQlInspection
        String query = "SELECT c FROM Client c WHERE c.firstname LIKE :regex OR c.surname LIKE :regex OR c.idClients LIKE :regex";
        List<Client> list = this.entitymanager.createQuery(query)
                .setParameter("regex", regex)
                .getResultList();
        for (Client client:list){
            if(!client.getActive()){
                list.remove(client);
            }
        }
        return list;
    }
}