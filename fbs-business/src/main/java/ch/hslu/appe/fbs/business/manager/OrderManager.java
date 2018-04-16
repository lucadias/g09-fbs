package ch.hslu.appe.fbs.business.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Class_Name
 *
 * @author Mischa Gruber
 */
public final class OrderManager {

    private static OrderManager instance = null;

    private static Object mutex = new Object();
    private HashMap<String, String> lockpool;

    private MessageDigest sha256Digest;

    public static OrderManager getInstance() {
        OrderManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new OrderManager();
                }
            }
        }
        return result;
    }

    private OrderManager() {

        try {
            this.sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
