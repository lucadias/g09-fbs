package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class OrderPersistor {

    private final EntityManager entitymanager = Util.entityManager;


    public Orders getById(int id) {
        Util.transactionBegin();

        System.out.println("Get Orders with id: " + String.valueOf(id));
        Orders order = entitymanager.find(Orders.class, id);

        System.out.println(order);

        Util.transactionCommit();


        return order;
    }


    public FBSFeedback save(Orders order) {
        return Util.save(order);
    }

    public List getAll(){
        Util.transactionBegin();

        List<Orders> list = this.entitymanager.createQuery("Select o From Orders o").getResultList();

        Util.transactionCommit();
        return list;
    }

}
