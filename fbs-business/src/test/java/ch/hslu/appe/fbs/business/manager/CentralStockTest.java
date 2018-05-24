package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.CentralStock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CentralStockTest {

    CentralStock cs = CentralStock.getInstance();


    @Test
    public void testGetCentralStockTest(){

        int amountOnCentralStock = cs.getCentralStock(123999999);

        cs.orderItem(123999999,3);


        assertEquals(97, cs.getCentralStock(123999999));
    }

    }



