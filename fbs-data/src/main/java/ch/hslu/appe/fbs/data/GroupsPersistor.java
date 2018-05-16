package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Groups;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Class for persisting Groups
 *
 * @author Luca Dias
 */
public class GroupsPersistor {

    private final EntityManager entitymanager = Util.entityManager;

    /**
     * Returns an Groups by its id.
     *
     * @param id int
     * @return Groups
     */
    public Groups getById(int id) {
        Util.transactionBegin();

        Groups groups = entitymanager.find(Groups.class, id);

        Util.transactionCommit();

        return groups;
    }




    public Groups getByGroupsNr(int groupsNr) {
        return this.getById(groupsNr);
    }

    /**
     * saves or updates an Groups.
     *
     * @param groups Groups
     * @return FBSFeedback
     */
    public FBSFeedback save(Groups groups) {
        return Util.save(groups);
    }

    /**
     * Returns all Groups.
     *
     * @return List&gt;Groups&lt;
     */
    public List<Groups> getList() {
        return this.entitymanager.createQuery("Select a From Groups a").getResultList();
    }

    /**
     * Returns a List of Groups resembling the searchText
     *
     * @param searchText String
     * @return List&gt;Groups&lt;
     */
    public List<Groups> getList(String searchText) {
        String regex = "%"+searchText+"%";
        //noinspection JpaQlInspection
        String query = "SELECT e FROM Groups e WHERE e.name LIKE :regex";

        return this.entitymanager.createQuery(query)
                .setParameter("regex", regex)
                .getResultList();
    }
}