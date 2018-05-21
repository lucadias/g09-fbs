package ch.hslu.appe.fbs.client;

import static ch.hslu.appe.fbs.client.Client.SESSION;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

/**
 * This class is responsible for the viewManagement
 * It hols the current view and provides methods to receive their contents
 * for the different areas of the gui
 * @author joel salzmann
 */
public final class JavaFXViewController {
    public static final String ARTICLE_SERVICE_NAME = "ArticleService";
    public static final String LOGIN_SERVICE_NAME = "LoginService";
    public static final String ORDER_SERVICE_NAME = "OrderService";
    public static final String ORDERSTATE_SERVICE_NAME = "OrderStateService";
    public static final String EMPLOYEE_SERVICE_NAME = "EmployeeService";
    public static final String CLIENT_SERVICE_NAME = "ClientService";
    private Node currentView;
    private ArrayList<RepaintRequestListener> repaintRequestListeners = new ArrayList<>();
    private static JavaFXViewController viewControllerInstance = null;
    

    private JavaFXViewController() {
        //empty
    }
    
    public static JavaFXViewController getInstance() {
        if(viewControllerInstance != null) {
            return viewControllerInstance;
        } else {
            viewControllerInstance = new JavaFXViewController();
            return viewControllerInstance;
        }
    }
    
    public void start() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/LoginView.fxml"));
            Parent loginView = (Parent) loader.load();
            LoginViewController loginViewController = (LoginViewController) loader.getController();
            this.currentView = loginView;
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
            //ToDo: handle this another way
            this.currentView = new GridPane();
        }
    }
    
    public void repaint() {
        for(RepaintRequestListener listener: this.repaintRequestListeners) {
            listener.repaint();
        }
    }
    
    public void addRepaintRequestListener(RepaintRequestListener listener) {
        this.repaintRequestListeners.add(listener);
    }
    
    public void setView(Node view) {
        this.currentView = view;
    }
    
    public Node getTopPane() {
        Node returnNode = new GridPane();
        if(SESSION != null) {
            try {
                FXMLLoader topMenuLoader = new FXMLLoader();
                topMenuLoader.setLocation(getClass().getResource("/fxml/TopMenu.fxml"));
                Parent topMenu = (Parent) topMenuLoader.load();
                MenuController menuController = (MenuController) topMenuLoader.getController();
                returnNode = topMenu;
            } catch (IOException e) {
                System.out.println("Error loading fxml: "+e.getMessage());
            }
        }
        return returnNode;
    }
    
    public Node getCenterPane() {
        return this.currentView;
    }
    
    public Node getLeftPane() {
        Node returnNode = new GridPane();
        if(SESSION != null) {
            try {
                FXMLLoader leftMenuLoader = new FXMLLoader();
                leftMenuLoader.setLocation(getClass().getResource("/fxml/LeftMenu.fxml"));
                Parent leftMenu = (Parent) leftMenuLoader.load();
                MenuController menuController = (MenuController) leftMenuLoader.getController();
                returnNode = leftMenu;
            } catch (IOException e) {
                System.out.println("Error loading fxml: "+e.getMessage());
            }
        }
        return returnNode;
    }    
    //ToDo: Think about implementing a state-pattern with the Menu-Buttons depending on the view
}
