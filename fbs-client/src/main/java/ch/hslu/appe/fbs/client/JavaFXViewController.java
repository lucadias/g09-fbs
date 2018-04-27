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
public final class JavaFXViewController implements StateChangeListener {
    public static final String ARTICLE_SERVICE_NAME = "ArticleService";
    public static final String LOGIN_SERVICE_NAME = "LoginService";
    public static final String ORDER_SERVICE_NAME = "OrderService";
    private Node currentView;
    private ArrayList<RepaintRequestListener> repaintRequestListeners = new ArrayList<>();
    

    public JavaFXViewController() {
        
    }
    
    public void start() {
//        //create loginView
//        //ToDo: Implement logged in check
//        LoginView loginView = new LoginView();
////        loginView.addLoginButtonListener(this);
//        this.setView(loginView);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/LoginView.fxml"));
            Parent loginView = (Parent) loader.load();
            LoginViewController loginViewController = (LoginViewController) loader.getController();
            this.stateChanged(loginView, loginViewController);
            this.currentView = loginView;
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
            //ToDo: handle this another way
            this.currentView = new GridPane();
        }
        
//        ArticleDetailView adv = new ArticleDetailView();
//        this.setView(adv);
        
//        ArticleEditView aev = new ArticleEditView();
//        this.setView(aev);
        
//        OrderDetailView odv = new OrderDetailView();
//        this.setView(odv);

//        ArticleListView alv = new ArticleListView();
//        this.setView(alv);
    }
    
    private void repaint() {
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
            //don't have to add listener because of leftMenu has the same controller but won't matter, is catched
            MenuController menuController = (MenuController) topMenuLoader.getController();
            this.stateChanged(topMenu, menuController);
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
            this.stateChanged(leftMenu, menuController);
            return leftMenu;
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
            return null;
        }
    }

    @Override
    public void stateChanged(Parent view, Object controller) {
        if(controller instanceof HasStateChangeListener) {
//            controller.addStateChangeListener(this);
            System.out.println("fuck you");
            ((HasStateChangeListener) controller).addStateChangeListener(this); //seems to be an endless loop
        }
        this.currentView = view;
        this.repaint();
    }
    
    //ToDo: Think about implementing a state-pattern with the Menu-Buttons depending on the view
}
