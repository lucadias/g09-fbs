package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class OrderPersistor {
    private final EntityManagerFactory emfactory;
    private final EntityManager entitymanager;

    public OrderPersistor() {
        this.emfactory = Persistence.createEntityManagerFactory("Glp9Pu");

        this.entitymanager = emfactory.createEntityManager();
    }

    public Orders getById(int id) {
        transactionBegin();

        Orders order = entitymanager.find(Orders.class, id);

        //code

        transactionClose();

        return order;
    }


    public FBSFeedback save(Orders order) {
        this.transactionBegin();
        Orders oldOrder = this.entitymanager.find(Orders.class, order.getIdOrders());

        if (oldOrder != null){
            if (oldOrder.equals(order)){
                transactionClose();
                return FBSFeedback.SUCCESS;
            }
            oldOrder = order;
        }else{
            this.entitymanager.persist(order);
        }

        this.transactionClose();
        return FBSFeedback.SUCCESS;
    }

    public List getAll(){
        return this.entitymanager.createQuery("Select o From Orders o").getResultList();
    }


    private void transactionBegin(){
        entitymanager.getTransaction().begin();

    }

    private void transactionClose(){
        entitymanager.close();
        emfactory.close();

    }
}
