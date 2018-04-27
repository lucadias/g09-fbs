package ch.hslu.appe.fbs.client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

/**
 * JavaDoc
 */
public final class JavaFXViewController {
    public static final String ARTICLE_SERVICE_NAME = "ArticleService";
    public static final String LOGIN_SERVICE_NAME = "LoginService";
    public static final String ORDER_SERVICE_NAME = "OrderService";
    private Node currentView;
    private ArrayList<RepaintRequestListener> repaintRequestListeners = new ArrayList<>();
    private static JavaFXViewController viewControllerInstance = null;
    

    private JavaFXViewController() {
        
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
        try {
            FXMLLoader topMenuLoader = new FXMLLoader();
            topMenuLoader.setLocation(getClass().getResource("/fxml/TopMenu.fxml"));
            Parent topMenu = (Parent) topMenuLoader.load();
            MenuController menuController = (MenuController) topMenuLoader.getController();
            return topMenu;
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
            return null;
        }
    }
    
    public Node getCenterPane() {
        return this.currentView;
    }
    
    public Node getLeftPane() {
        try {
            FXMLLoader leftMenuLoader = new FXMLLoader();
            leftMenuLoader.setLocation(getClass().getResource("/fxml/LeftMenu.fxml"));
            Parent leftMenu = (Parent) leftMenuLoader.load();
            MenuController menuController = (MenuController) leftMenuLoader.getController();
            return leftMenu;
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
            return null;
        }
    }    
    //ToDo: Think about implementing a state-pattern with the Menu-Buttons depending on the view
}
