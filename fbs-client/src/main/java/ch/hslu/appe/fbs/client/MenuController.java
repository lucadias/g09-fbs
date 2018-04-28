package ch.hslu.appe.fbs.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author salzm
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
            System.out.println("Error loading fxml: "+e.getMessage());
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
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }
    
    @FXML
    public void orderButtonPressed(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/OrderListView.fxml"));
//            Parent orderListView = loader.load();
//            for(StateChangeListener listener:stateChangeListeners) {
//                listener.stateChanged(orderListView);
//            }
//        } catch (IOException e) {
//            System.out.println("Error loading fxml: "+e.getMessage());
//        }
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
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/LogView.fxml"));
//            Parent logView = loader.load();
//            for(StateChangeListener listener:stateChangeListeners) {
//                listener.stateChanged(logView);
//            }
//        } catch (IOException e) {
//            System.out.println("Error loading fxml: "+e.getMessage());
//        }
    }    
}
