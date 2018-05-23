

package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.stock.Reservation;
import ch.hslu.appe.stock.Stock;
import ch.hslu.appe.stock.StockException;
import ch.hslu.appe.stock.StockFactory;

import java.time.Instant;


public class CentralStock {


    private static CentralStock instance = null;

    private static Object mutex = new Object();

    private int count;

    Stock stock = StockFactory.getStock();

    public static CentralStock getInstance() {
        CentralStock result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new CentralStock();
                }
            }
        }
        return result;
    }

    private CentralStock() {
    }


    public int getCentralStock(int articleNr){

        try {
            count = stock.getItemCount(convertArticleNrtoStockNr(articleNr));
            System.out.println("Es hat " + count + " Stück an Lager.");
            return count;
        } catch (StockException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public long orderItem(int articleNr, int amount){
        try {
            return stock.orderItem(convertArticleNrtoStockNr(articleNr),amount);
        } catch (StockException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public long reserveItem(String articleNr, int amount){
        try {
            return stock.reserveItem(articleNr,amount);
        } catch (StockException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Instant getItemDeliveryDate(String key){

        try {
            return stock.getItemDeliveryDate(key);
        } catch (StockException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertArticleNrtoStockNr(int articleNr){
       String Stocknr = String.valueOf(articleNr);

       while(Stocknr.length() > 6){
           Stocknr = Stocknr.substring(0, Stocknr.length() - 1);
       }
        while(Stocknr.length() != 6){
            Stocknr = "0" + Stocknr;
        }

        return Stocknr;

    }


}

