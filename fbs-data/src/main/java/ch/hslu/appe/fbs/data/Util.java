package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;

public class Util {

    private static final EntityManager entitymanager = DBEntityManager.em;

    // Todo: Create Entity superclass if possible
    public static FBSFeedback save(Object entity) {
        try {
            Util.entitymanager.getTransaction().begin();
            entitymanager.merge(entity);
            entitymanager.flush();
            Util.entitymanager.getTransaction().commit();
            return FBSFeedback.SUCCESS;
        } finally {
            return FBSFeedback.UNKNOWN_ERROR;
        }
    }
}
