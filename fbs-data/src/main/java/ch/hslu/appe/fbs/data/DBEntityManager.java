package ch.hslu.appe.fbs.data;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DBEntityManager {
    public static final EntityManager em = Persistence.createEntityManagerFactory("Glp9Pu").createEntityManager();
}
