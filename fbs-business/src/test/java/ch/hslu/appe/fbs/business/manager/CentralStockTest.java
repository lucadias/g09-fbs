package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.CentralStock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CentralStockTest {

    CentralStock cs = CentralStock.getInstance();


    @Test
    public void getCentralStockTest(){

        int amountOnCentralStock = cs.getCentralStock(123999999);

        cs.orderItem(123999999,3);


        assertEquals(92, cs.getCentralStock(123999999));
    }


    @Test
    public void orderItemTest(){
        System.out.println(cs.orderItem(123999999,5));
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



