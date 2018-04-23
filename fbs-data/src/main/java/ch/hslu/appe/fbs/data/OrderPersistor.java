package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class OrderPersistor {

    private final EntityManager entitymanager = DBEntityManager.em;


    public Orders getById(int id) {
        transactionBegin();

        Orders order = entitymanager.find(Orders.class, id);

        transactionCommit();
        //code

        return order;
    }




    public FBSFeedback save(Orders order) {
        try {
            transactionBegin();
            entitymanager.merge(order);
            entitymanager.flush();
        } finally {
            this.transactionCommit();
        }
        return FBSFeedback.SUCCESS;
    }

    public List getAll(){
        transactionBegin();
        List<Orders> list = new ArrayList<>();
        list = this.entitymanager.createQuery("Select o From Orders o").getResultList();

        this.transactionCommit();
        return list;
    }


    private void transactionBegin(){
        entitymanager.getTransaction().begin();
    }

    private void transactionCommit() {
        entitymanager.getTransaction().commit();
    }
}
