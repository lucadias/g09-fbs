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

    private static OrderPersistor persistor = new OrderPersistor();
    private static Orders orders = new Orders();
    private static Orders orders2 = new Orders();
    private static List<Orders> list = new ArrayList<>();

    @BeforeClass
    public static void init(){

        orders.setIdOrders(9999);
        orders.setClientIdClients(2);
        orders.setEmployeeIdEmployee(1);
        orders.setOrderStateIdOrderState(1);
        list.add(orders);
        orders2.setIdOrders(9998);
        orders2.setClientIdClients(2);
        orders2.setEmployeeIdEmployee(2);
        orders2.setOrderStateIdOrderState(1);
        list.add(orders2);
    }

    @Test
    public void testSave(){
        assertEquals(FBSFeedback.SUCCESS, persistor.save(orders));
        assertEquals(FBSFeedback.SUCCESS, persistor.save(orders2));
    }

    @Test
    public void testGetById(){
        assertEquals(orders, persistor.getById(9999));
    }

    @Test
    @Ignore
    public void testGetAll(){
        assertEquals(list.size(), persistor.getAll().size());
    }

    @AfterClass
    public static void clean(){
        DBEntityManager.em.getTransaction().begin();
        DBEntityManager.em.createQuery("delete from Orders where idOrders = 9999").executeUpdate();
        DBEntityManager.em.createQuery("delete from Orders where idOrders = 9998").executeUpdate();
        DBEntityManager.em.getTransaction().commit();
    }
}
