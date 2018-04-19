package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class OrderPersistor {
    private final EntityManager entitymanager;

    public OrderPersistor() {

        this.entitymanager = DBEntityManager.em;
    }

    public Orders getById(int id) {
        transactionBegin();

        Orders order = entitymanager.find(Orders.class, id);

        //code

        return order;
    }


    public FBSFeedback save(Orders order) {
        try {
            transactionBegin();
            entitymanager.merge(order);
            entitymanager.flush();
        } finally {
            entitymanager.getTransaction().commit();
        }
        return FBSFeedback.SUCCESS;
    }

    public List getAll(){
        return this.entitymanager.createQuery("Select o From Orders o").getResultList();
    }


    private void transactionBegin(){
        entitymanager.getTransaction().begin();
    }

}
