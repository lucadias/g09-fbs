package ch.hslu.appe.fbs.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.Client.SESSION;
import static ch.hslu.appe.fbs.client.JavaFXViewController.LOGIN_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteLoginService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private RemoteLoginService loginService = null;
    
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
        try {
            final String urlString = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + LOGIN_SERVICE_NAME;
            this.loginService = (RemoteLoginService) Naming.lookup(urlString);
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            System.out.println("Error in RMI: "+e);
        }
    }
    
    @FXML
    public void loginButtonPressed(ActionEvent event) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String username = this.usernameInput.getText();
            String password = this.passwordInput.getText();
            String passwordHash = this.sha256(password);
            if(this.checkLogin(username, String.valueOf(passwordHash))) {
                Client.username = username;
                this.showDashboard();
            } else {
                System.out.println("Login failed");
            }
        } catch(NoSuchAlgorithmException e) {
            System.out.println("Problems with password:"+e.getMessage());
        }        
    }
    
    private String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }
    
    private void showDashboard() {
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
    
    private Boolean checkLogin(String username, String password) {
        Boolean loginSuccess = false;
        if(this.loginService != null) {
            try {
                SESSION = this.loginService.login(username, password);
                if(SESSION != null) {
                    loginSuccess = true;
                } 
            } catch (RemoteException e) {
                System.out.println("Problems with login:"+e.getMessage());
            }
        }
        return loginSuccess;
    }
}
