package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Class for shared methods of Persistors
 */
public class Util {

    public static final EntityManager entityManager = Persistence.createEntityManagerFactory("Glp9Pu").createEntityManager();


    // Todo: Create Entity superclass if possible

    /**
     * universal save/update method
     * @param entity
     * @return
     */
    public static FBSFeedback save(Object entity) {
        try {
            Util.entityManager.getTransaction().begin();
            Util.entityManager.merge(entity);
            Util.entityManager.flush();
            Util.entityManager.getTransaction().commit();
            return FBSFeedback.SUCCESS;
        } catch (Exception e){
            System.out.println(e.toString());
        }
        Util.entityManager.getTransaction().commit();
        return FBSFeedback.UNKNOWN_ERROR;
    }
}
