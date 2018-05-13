package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.data.OrderPersistor;
import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class OrderPersistorTest {

    private static OrderPersistor persistor = new OrderPersistor();
    private static Orders orders = new Orders();
    private static Orders orders2 = new Orders();
    private static List<Orders> list = new ArrayList<>();

    @BeforeClass
    public static void init(){

        orders.setIdOrders(1);
        orders.setClientIdClients(2);
        orders.setDate(Timestamp.from(Instant.now()));
        orders.setTotalPrice(130.00);
        orders.setEmployeeIdEmployee(7);
        orders.setOrderStateIdOrderState(1);
        list.add(orders);

        orders2.setIdOrders(2);
        orders2.setClientIdClients(1);
        orders2.setDate(Timestamp.from(Instant.now()));
        orders2.setTotalPrice(180.00);
        orders2.setEmployeeIdEmployee(8);
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
    public void testGetAll(){
        assertEquals(list.size(), persistor.getAll().size());
    }

    @AfterClass
    public static void clean(){
        Util.entityManager.getTransaction().begin();
        Util.entityManager.createQuery("delete from Orders where idOrders = 9999").executeUpdate();
        Util.entityManager.createQuery("delete from Orders where idOrders = 9998").executeUpdate();
        Util.entityManager.getTransaction().commit();
    }
}
