package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.EmployeeGroups;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Class for persisting EmployeeGroupss
 *
 * @author Luca Dias
 */
public class EmployeeGroupsPersistor {

    private final EntityManager entitymanager = Util.entityManager;

    /**
     * Returns an EmployeeGroups by its id.
     *
     * @param id int
     * @return EmployeeGroups
     */
    public EmployeeGroups getById(int id) {
        Util.transactionBegin();

        EmployeeGroups employeeGroups = entitymanager.find(EmployeeGroups.class, id);

        Util.transactionCommit();

        return employeeGroups;
    }




    public EmployeeGroups getByEmployeeGroupsNr(int employeeGroupsNr) {
        return this.getById(employeeGroupsNr);
    }

    /**
     * saves or updates an EmployeeGroups.
     *
     * @param employeeGroups EmployeeGroups
     * @return FBSFeedback
     */
    public FBSFeedback save(EmployeeGroups employeeGroups) {
        return Util.save(employeeGroups);
    }

    /**
     * Returns all EmployeeGroupss.
     *
     * @return List&gt;EmployeeGroups&lt;
     */
    public List<EmployeeGroups> getList() {
        return this.entitymanager.createQuery("Select a From EmployeeGroups a").getResultList();
    }

    /**
     * Returns a List of EmployeeGroupss resembling the searchText
     *
     * @param searchText String
     * @return List&gt;EmployeeGroups&lt;
     */
    public List<EmployeeGroups> getList(String searchText) {
        String regex = "%"+searchText+"%";
        //noinspection JpaQlInspection
        String query = "SELECT e FROM EmployeeGroups e WHERE e.firstname LIKE :regex OR e.surname LIKE :regex OR e.idEmployeeGroupss LIKE :regex";

        return this.entitymanager.createQuery(query)
                .setParameter("regex", regex)
                .getResultList();
    }
}