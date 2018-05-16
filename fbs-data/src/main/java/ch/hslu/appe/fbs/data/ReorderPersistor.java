package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Reorder;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Class for persisting Reorder
 *
 * @author Luca Dias
 */
public class ReorderPersistor {

    private final EntityManager entitymanager = Util.entityManager;

    /**
     * Returns an Reorder by its id.
     *
     * @param id int
     * @return Reorder
     */
    public Reorder getById(int id) {
        Util.transactionBegin();

        Reorder reorder = entitymanager.find(Reorder.class, id);

        Util.transactionCommit();

        return reorder;
    }




    public Reorder getByReorderNr(int reorderNr) {
        return this.getById(reorderNr);
    }

    /**
     * saves or updates an Reorder.
     *
     * @param reorder Reorder
     * @return FBSFeedback
     */
    public FBSFeedback save(Reorder reorder) {
        return Util.save(reorder);
    }

    /**
     * Returns all Reorder.
     *
     * @return List&gt;Reorder&lt;
     */
    public List<Reorder> getList() {
        return this.entitymanager.createQuery("Select a From Reorder a").getResultList();
    }

    /**
     * Returns a List of Reorder resembling the searchText
     *
     * @param searchText String
     * @return List&gt;Reorder&lt;
     */
    public List<Reorder> getList(String searchText) {
        String regex = "%"+searchText+"%";
        //noinspection JpaQlInspection
        String query = "SELECT e FROM Reorder e WHERE e.name LIKE :regex";

        return this.entitymanager.createQuery(query)
                .setParameter("regex", regex)
                .getResultList();
    }
}