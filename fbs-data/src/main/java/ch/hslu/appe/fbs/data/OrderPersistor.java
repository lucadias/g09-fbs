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
     * Returns List of Orders matching a Client id.
     * @param id int Client Id
     * @return List&gt;Orders&lt;
     */
    public List<Orders> getListByClientId(int id){
        String query = "SELECT o FROM Orders o WHERE o.clientIdClients = :id";
        return this.entitymanager.createQuery(query)
                .setParameter("id", id)
                .getResultList();
    }


    /**
     * saves or updates an Order.
     * @param order Orders
     * @return FBSFeedback
     */
    public Orders save(Orders order) {
        try {
            Util.transactionBegin();
            if(order.getIdOrders() == 0){
                Util.entityManager.persist(order);
            } else {
                Util.entityManager.merge(order);
            }
            Util.entityManager.flush();
            Util.transactionCommit();
        } catch (Exception e){
            System.out.println(e.toString());
            Util.transactionCommit();
        }
        return order;
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
