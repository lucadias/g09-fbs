/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

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
public class DashboardViewController implements Initializable {
    
    @FXML
    private Button dashboardNewOrderButton;
    
    @FXML
    private Button dashboardArticleListButton;
    
    @FXML
    private Button dashboardLogoutButton;
    
    @FXML
    public void showArticleList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ArticleListView.fxml"));
            Parent articles = (Parent) loader.load();
            ArticleListViewController articleListViewController = (ArticleListViewController) loader.getController();
            JavaFXViewController.getInstance().setView(articles);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }
    
    @FXML
    public void showNewOrder(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/OrderEditView.fxml"));
//            Parent orderEdit = (Parent) loader.load();
//            OrderEditViewController orderEditViewController = (OrderEditViewController) loader.getController();
//            orderEditViewController.setId(-1);
//            JavaFXViewController.getInstance().setView(orderEdit);
//            JavaFXViewController.getInstance().repaint();
//        } catch (IOException e) {
//            System.out.println("Error loading fxml: "+e.getMessage());
//        }
try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderDetailView.fxml"));
            Parent order = (Parent) loader.load();
            OrderDetailViewController orderDetailViewController = (OrderDetailViewController) loader.getController();
            orderDetailViewController.setId(10002);
            JavaFXViewController.getInstance().setView(order);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
}
