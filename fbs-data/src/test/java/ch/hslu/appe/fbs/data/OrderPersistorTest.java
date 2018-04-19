package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.data.OrderPersistor;
import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderPersistorTest {

    private OrderPersistor persistor = new OrderPersistor();
    private Orders orders = new Orders();

    @Before
    public void init(){
        this.orders.setIdOrders(1234);
        this.orders.setClientIdClients(1234);
        this.orders.setEmployeeIdEmployee(1234);
    }

    @Test
    public void testSave(){
        assertEquals(FBSFeedback.SUCCESS, this.persistor.save(orders));
    }
}
