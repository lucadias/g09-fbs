package ch.hslu.appe.fbs.business;

import ch.hslu.appe.fbs.business.services.*;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server component for the FBS.
 *
 * @author Mischa Gruber
 */
public final class FBSServer {

    public static final int REGISTRY_PORT = 1099;
    public static final String ARTICLE_SERVICE_NAME = "ArticleService";
    public static final String ORDER_SERVICE_NAME = "OrderService";
    public static final String LOGIN_SERVICE_NAME = "LoginService";
    public static final String CLIENT_SERVICE_NAME = "ClientService";
    public static final String EMPLOYEE_SERVICE_NAME = "EmployeeService";
    public static final String ORDER_STATE_SERVICE_NAME = "OrderStateService";
    public static final String LOG_SERVICE_NAME = "LogService";
    public static final String PERMISSION_SERVICE_NAME = "PermissionService";

    private static ArticleService articleService;
    private static OrderService orderService;
    private static LoginService loginService;
    private static ClientService clientService;
    private static EmployeeService employeeService;
    private static OrderStateService orderStateService;
    private static LogService logService;
    private static PermissionService permissionService;

    static {
        try {
            FBSServer.articleService = new ArticleService();
            FBSServer.orderService = new OrderService();
            FBSServer.loginService = new LoginService();
            FBSServer.clientService = new ClientService();
            FBSServer.employeeService = new EmployeeService();
            FBSServer.orderStateService = new OrderStateService();
            FBSServer.logService = new LogService();
            FBSServer.permissionService = new PermissionService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor of the FBSServer.
     */
    private FBSServer() {
        Registry registry;

        // Create Registry
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

        // Bind Services
        try {
            final String urlArticleService = "rmi://localhost:"
                    + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            Naming.bind(urlArticleService, FBSServer.articleService);

            final String urlOrderService = "rmi://localhost:"
                    + String.valueOf(REGISTRY_PORT) + "/" + ORDER_SERVICE_NAME;
            Naming.bind(urlOrderService, FBSServer.orderService);

            final String urlLoginService = "rmi://localhost:"
                    + String.valueOf(REGISTRY_PORT) + "/" + LOGIN_SERVICE_NAME;
            Naming.bind(urlLoginService, FBSServer.loginService);

            final String urlClientService = "rmi://localhost:"
                    + String.valueOf(REGISTRY_PORT) + "/" + CLIENT_SERVICE_NAME;
            Naming.bind(urlClientService, FBSServer.clientService);

            final String urlEmployeeService = "rmi://localhost:"
                    + String.valueOf(REGISTRY_PORT) + "/" + EMPLOYEE_SERVICE_NAME;
            Naming.bind(urlEmployeeService, FBSServer.employeeService);

            final String urlOrderStateService = "rmi://localhost:"
                    + String.valueOf(REGISTRY_PORT) + "/" + ORDER_STATE_SERVICE_NAME;
            Naming.bind(urlOrderStateService, FBSServer.orderStateService);

            final String urlLogService = "rmi://localhost:"
                    + String.valueOf(REGISTRY_PORT) + "/" + LOG_SERVICE_NAME;
            Naming.bind(urlLogService, FBSServer.logService);

            final String urlPermissionService = "rmi://localhost:"
                    + String.valueOf(REGISTRY_PORT) + "/" + PERMISSION_SERVICE_NAME;
            Naming.bind(urlPermissionService, FBSServer.permissionService);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to start the server.
     * @param args not used
     */
    public static void main(final String[] args) {
        new FBSServer();
    }
}
