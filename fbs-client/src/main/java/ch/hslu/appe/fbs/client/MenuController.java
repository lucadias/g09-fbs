package ch.hslu.appe.fbs.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 * This class is responsible for the menuViews leftMenu and topMenu and defines the methods for those views
 * @author joel salzmann
 */
public class MenuController implements Initializable {
    
    @FXML 
    private Button leftMenuDashboardButton;
    
    @FXML 
    private Button leftMenuArticleButton;
    
    @FXML 
    private Button leftMenuOrderButton;
    
    @FXML 
    private Button leftMenuDeliveryButton;
    
    @FXML 
    private Button leftMenuLogButton;
    
    @FXML
    private Label topMenuUsername;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        this.topMenuUsername.setText(Client.username); not working
    }
    
    @FXML
    public void dashboardButtonPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DashboardView.fxml"));
            Parent dashboard = (Parent) loader.load();
            DashboardViewController dashboardViewController = (DashboardViewController) loader.getController();
            JavaFXViewController.getInstance().setView(dashboard);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: ");
            e.printStackTrace();
        }
    }
    
    @FXML
    public void articleButtonPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ArticleListView.fxml"));
            Parent dashboard = (Parent) loader.load();
            ArticleListViewController articleListViewController = (ArticleListViewController) loader.getController();
            JavaFXViewController.getInstance().setView(dashboard);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: ");
            e.printStackTrace();
        }
    }
    
    @FXML
    public void orderButtonPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderListView.fxml"));
            Parent orders = (Parent) loader.load();
            OrderListViewController orderListViewController = (OrderListViewController) loader.getController();
            JavaFXViewController.getInstance().setView(orders);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: ");
            e.printStackTrace();
        }
    }
    
    @FXML
    public void deliveryButtonPressed(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/DeliveryView.fxml"));
//            Parent deliveryView = loader.load();
//            for(StateChangeListener listener:stateChangeListeners) {
//                listener.stateChanged(deliveryView);
//            }
//        } catch (IOException e) {
//            System.out.println("Error loading fxml: "+e.getMessage());
//        }
    }
    
    @FXML
    public void logButtonPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/LogView.fxml"));
            Parent logView = loader.load();
            JavaFXViewController.getInstance().setView(logView);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: ");
            e.printStackTrace();
        }
    }    
}
