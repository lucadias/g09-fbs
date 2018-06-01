package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Class for shared methods of Persistors.
 */
public class Util {

    /**
     * single entityManager for all BD transactions.
     */
    static final EntityManager entityManager = Persistence.createEntityManagerFactory("Glp9Pu").createEntityManager();

    /**
     * Private Constructor to prevent instances.
     */
    private Util() {
        // Do not fill.
    }

    /**
     * universal save/update method
     * @param entity generic Entity
     * @return FBSFeedback
     */
    static FBSFeedback save(final Object entity) {
        try {
            Util.transactionBegin();
            Util.entityManager.merge(entity);
            Util.entityManager.flush();
            Util.transactionCommit();
            return FBSFeedback.SUCCESS;
        } catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            Util.transactionCommit();
            return FBSFeedback.UNKNOWN_ERROR;
        }
    }

    /**
     * Begin DB transaction.
     */
    static void transactionBegin() {
        Util.entityManager.getTransaction().begin();
    }

    /**
     * Commit DB transaction.
     */
    static void transactionCommit() {
        Util.entityManager.getTransaction().commit();
    }
}
