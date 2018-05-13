package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Class for Persisting Orders
 *
 * @author Pascal Stalder
 */
public class OrderPersistor {

    private final EntityManager entitymanager = Util.entityManager;

    /**
     * Returns an Order by its Id.
     * @param id int
     * @return Orders
     */
    public Orders getById(int id) {
        Util.transactionBegin();

        System.out.println("Get Orders with id: " + String.valueOf(id));
        Orders order = entitymanager.find(Orders.class, id);

        System.out.println(order);

        Util.transactionCommit();


        return order;
    }


    /**
     * saves or updates an Order.
     * @param order Orders
     * @return FBSFeedback
     */
    public FBSFeedback save(Orders order) {
        return Util.save(order);
    }

    /**
     * Returns all Orders.
     * @return List&gt;Orders&lt;
     */
    public List<Orders> getAll(){
        Util.transactionBegin();

        List<Orders> list = this.entitymanager.createQuery("Select o From Orders o").getResultList();

        Util.transactionCommit();
        return list;
    }

}
