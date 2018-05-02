package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * JUnit Test of OrderDateAscComparator.
 *
 * @author Mischa Gruber
 */
public class OrderDateAscComparatorTest {

    @Test
    public void testSorting() {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        OrderDTO order1 = new OrderDTO(1);
        order1.setDate(new Timestamp(2018,6,12,17,45,0,0));
        OrderDTO order2 = new OrderDTO(2);
        order2.setDate(new Timestamp(2018,6,10,17,45,0,0));
        orderDTOs.add(order1);
        orderDTOs.add(order2);

        Comparator<OrderDTO> comparator = new OrderDateAscComparator();
        Collections.sort(orderDTOs, comparator);

        assertEquals(2, orderDTOs.get(0).getId());
        assertEquals(1, orderDTOs.get(1).getId());
    }
}
