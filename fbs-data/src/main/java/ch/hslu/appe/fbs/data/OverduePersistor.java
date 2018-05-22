package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Overdue;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Class for persisting Overdue
 *
 * @author Luca Dias
 */
public class OverduePersistor {

    private final EntityManager entitymanager = Util.entityManager;

    /**
     * Returns an Overdue by its id.
     *
     * @param id int
     * @return Overdue
     */
    public Overdue getById(int id) {
        Util.transactionBegin();

        Overdue overdue = entitymanager.find(Overdue.class, id);

        Util.transactionCommit();

        return overdue;
    }




    /**
     * saves or updates an Overdue.
     *
     * @param overdue Overdue
     * @return FBSFeedback
     */
    public FBSFeedback save(Overdue overdue) {
        return Util.save(overdue);
    }

    /**
     * Returns all Overdue.
     *
     * @return List&gt;Overdue&lt;
     */
    public List<Overdue> getList() {
        return this.entitymanager.createQuery("Select a From Overdue a").getResultList();
    }

    /**
     * Returns a List of Overdue resembling the searchText
     *
     * @param searchText String
     * @return List&gt;Overdue&lt;
     */
    public List<Overdue> getList(String searchText) {
        String regex = "%"+searchText+"%";
        //noinspection JpaQlInspection
        String query = "SELECT e FROM Overdue e WHERE e.name LIKE :regex";

        return this.entitymanager.createQuery(query)
                .setParameter("regex", regex)
                .getResultList();
    }
}