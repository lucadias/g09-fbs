package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.CentralStock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CentralStockTest {

    CentralStock cs = CentralStock.getInstance();


    @Test
    public void getCentralStockTest(){
        int amountonCentralStock = cs.getCentralStock(123);

        cs.orderItem(123,3);

        assertEquals(92, cs.getCentralStock(123));
    }


    @Test
    public void orderItemTest(){
        System.out.println(cs.orderItem(123,5));
    }

    @Test
    public void reserveItemTest(){
        System.out.println(cs.reserveItem("123123",5));
    }


    @Test
    public void getItemDeliveryDateTest(){
        System.out.println(cs.getItemDeliveryDate("123123"));
    }
}
