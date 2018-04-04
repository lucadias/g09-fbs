package ch.hslu.appe.fbs.business;

import ch.hslu.appe.fbs.business.services.ArticleService;

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
            final String url = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            Naming.bind(url, articleService);
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
