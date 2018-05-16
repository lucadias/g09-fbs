package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Bill;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Class for persisting Bill
 *
 * @author Luca Dias
 */
public class BillPersistor {

    private final EntityManager entitymanager = Util.entityManager;

    /**
     * Returns an Bill by its id.
     *
     * @param id int
     * @return Bill
     */
    public Bill getById(int id) {
        Util.transactionBegin();

        Bill bill = entitymanager.find(Bill.class, id);

        Util.transactionCommit();

        return bill;
    }




    public Bill getByBillNr(int billNr) {
        return this.getById(billNr);
    }

    /**
     * saves or updates an Bill.
     *
     * @param bill Bill
     * @return FBSFeedback
     */
    public FBSFeedback save(Bill bill) {
        return Util.save(bill);
    }

    /**
     * Returns all Bill.
     *
     * @return List&gt;Bill&lt;
     */
    public List<Bill> getList() {
        return this.entitymanager.createQuery("Select a From Bill a").getResultList();
    }

    /**
     * Returns a List of Bill resembling the searchText
     *
     * @param searchText String
     * @return List&gt;Bill&lt;
     */
    public List<Bill> getList(String searchText) {
        String regex = "%"+searchText+"%";
        //noinspection JpaQlInspection
        String query = "SELECT e FROM Bill e WHERE e.name LIKE :regex";

        return this.entitymanager.createQuery(query)
                .setParameter("regex", regex)
                .getResultList();
    }
}