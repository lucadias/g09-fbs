package ch.hslu.appe.fbs.business;

import ch.hslu.appe.fbs.business.services.ArticleService;
import ch.hslu.appe.fbs.business.services.OrderService;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class FBSServer {

    public static final int REGISTRY_PORT = 1099;
    public static final String ARTICLE_SERVICE_NAME = "ArticleService";
    public static final String ORDER_SERVICE_NAME = "OrderService";

    protected FBSServer() {
        Registry registry;

        try {
            registry = LocateRegistry.createRegistry(REGISTRY_PORT);

            new Thread(() -> {
                synchronized (registry) {
                    try {
                        registry.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            final ArticleService articleService = new ArticleService();
            final String urlArticleService = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            Naming.bind(urlArticleService, articleService);

            final OrderService orderService = new OrderService();
            final String urlOrderService = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ORDER_SERVICE_NAME;
            Naming.bind(urlOrderService, orderService);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        new FBSServer();
    }
}
