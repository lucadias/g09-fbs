package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.manager.OrderManager;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class OrderManagerTest {

    @Test
    public void testGetOrderById() {
        OrderManager orderManager = OrderManager.getInstance();

        //OrderDTO orderDTO1 = orderManager.getById(1);
        OrderDTO orderDTO2 = orderManager.getById(10002);

        //assertNotEquals(null, orderDTO1);
        assertNotEquals(null, orderDTO2);
    }
}
