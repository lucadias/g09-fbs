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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author salzm
 */
public class LoginViewController implements Initializable {
    
    @FXML 
    private Label title;
    
    @FXML 
    private Label usernameLabel;
    
    @FXML 
    private Label passwordLabel;
    
    @FXML 
    private TextField usernameInput;
    
    @FXML 
    private TextField passwordInput;
    
    @FXML 
    private Button loginButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void loginButtonPressed(ActionEvent event) {
        //ToDo: Check login
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
}
