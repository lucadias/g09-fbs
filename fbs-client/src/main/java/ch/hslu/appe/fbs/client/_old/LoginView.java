/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client._old;

import static ch.hslu.appe.fbs.client._old.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client._old.JavaFXViewController.LOGIN_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.RemoteArticleService;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author salzm
 */
public final class LoginView implements FBSView {
    private EmployeeDTO employeeDTO;
    //ToDo: add ObserverPattern
    private ArrayList<LoginButtonListener> buttonListeners = new ArrayList<>();
    
    
    public LoginView() {
//        try {
//            final String url = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + LOGIN_SERVICE_NAME;
//            final RemoteEmployeeService loginService = (RemoteArticleService) Naming.lookup(url);
//            EmployeeDTO currentArticle = loginService.getById(1);
//            this.employeeDTO = currentArticle;
//        } catch (NotBoundException | MalformedURLException |
//            RemoteException e) {
//            this.employeeDTO = new EmployeeDTO(Integer.MAX_VALUE);
//            this.employeeDTO.setDescription("Error in RMI: "+e);
//            System.out.println("Error in RMI: "+e);
//        }
    }
    
    public void addLoginButtonListener(LoginButtonListener listener) {
        this.buttonListeners.add(listener);
    }
    
    private void loginButtonEventFired() {
        for(LoginButtonListener listener: this.buttonListeners) {
            listener.loginButtonPressed();
        }
    }

    @Override
    public Node getCenterPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        
        Text title = new Text("Filial-Bestellsystem");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        grid.add(title, 1, 1, 2, 1);
        
        Text username = new Text("Benutzername");
        username.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(username, 1, 2);
        
        TextField usernameValue = new TextField();
        usernameValue.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
//        usernameValue.setText(Integer.toString(this.employeeDTO.getUsername()));
        grid.add(usernameValue, 2, 2);
        
        Text password = new Text("Passwort");
        password.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(password, 1, 3);
        
        TextField passwordValue = new TextField();
        passwordValue.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
//        passwordValue.setText(Integer.toString(this.employeeDTO.getPassword()));
        grid.add(passwordValue, 2, 3);
        
        Button loginButton = new Button("Einloggen");
        loginButton.setPrefSize(100, 20);
        grid.add(loginButton, 1, 4);
        
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                loginButtonEventFired();
            }
        });
        
        return grid;
    }

    @Override
    public Node getBottomPane() {
        return new GridPane();
    }
    
}
