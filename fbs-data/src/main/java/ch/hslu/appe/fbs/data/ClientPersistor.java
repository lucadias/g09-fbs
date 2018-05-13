package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.model.entities.Client;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;


//Hotfix


/**
 * JavaDoc
 *
 * @author Pascal Stalder
 */
public class ClientPersistor {

    private final EntityManager entitymanager = Util.entityManager;


    public Client getById(int id) {
        Util.transactionBegin();
        Client client = entitymanager.find(Client.class, id);


        //code

        Util.transactionCommit();

        return client;
    }

    public Client getByClientNr(int clientNr) { return this.getById(clientNr);}

    public FBSFeedback save(Client client) { return Util.save(client); }


    public List<Client> getList() {
        return this.entitymanager.createQuery("Select c From Client c").getResultList();
    }

    public List<Client> search(String regEx) {

        List<Client> result = new ArrayList<>();
        for (Client client:this.getList()){
            if(client.getFirstname().matches(regEx) || client.getSurname().matches(regEx)) {
                result.add(client);
            }
        }
        return result;
    }

    public List<Client> getList(String regEx){
        String regex = "%"+regEx+"%";
        String query = "SELECT c FROM Client c WHERE c.firstname LIKE :regex OR c.surname LIKE :regex OR c.idClients LIKE :regex";
            return this.entitymanager.createQuery(query)
                    .setParameter("regex", regEx)
                    .getResultList();

    }

}