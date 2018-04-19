package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.data.OrderPersistor;
import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderPersistorTest {

    private OrderPersistor persistor = new OrderPersistor();
    private Orders orders = new Orders();
    private Orders orders2 = new Orders();
    private List<Orders> list = new ArrayList<>();
    private EntityManagerFactory emfactory;
    private EntityManager entitymanager;

    @Before
    public void init(){

        this.orders.setIdOrders(1);
        this.orders.setClientIdClients(2);
        this.orders.setEmployeeIdEmployee(1);
        this.orders.setOrderStateIdOrderState(1);
        this.list.add(orders);
        this.orders2.setIdOrders(2);
        this.orders2.setClientIdClients(2);
        this.orders2.setEmployeeIdEmployee(2);
        this.orders2.setOrderStateIdOrderState(1);
        this.list.add(orders2);
    }

    @Test
    public void testSave(){
        assertEquals(FBSFeedback.SUCCESS, this.persistor.save(orders));
        assertEquals(FBSFeedback.SUCCESS, this.persistor.save(orders2));
    }

    @Test
    public void testGetById(){
        assertEquals(this.orders, this.persistor.getById(1));
    }

    @Test
    public void testGetAll(){
        // assertEquals(this.list, this.persistor.getAll());
    }

    @AfterClass
    public static void clean(){
        DBEntityManager.em.createQuery("delete from Orders where idOrders = 1").executeUpdate();
        DBEntityManager.em.createQuery("delete from Orders where idOrders = 2").executeUpdate();
    }
}
