package ch.hslu.appe.fbs.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.Client.SESSION;
import static ch.hslu.appe.fbs.client.JavaFXViewController.LOG_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteLogService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author salzm
 */
public class LogViewController implements Initializable {
    private RemoteLogService logService;
    private List<String> logList;
    
    @FXML
    private GridPane logGrid;
    
    @FXML
    private Button refreshButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final String urlString = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + LOG_SERVICE_NAME;
            this.logService = (RemoteLogService) Naming.lookup(urlString);
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            System.out.println("Error in RMI: "+e);
        }
    }
    
    @FXML
    public void getNewLogs(ActionEvent event) {
        try {
            this.logList = this.logService.getLogList(SESSION);
            int i = 0;
            for(String logEntry : this.logList) {
                Text logEntryText = new Text();
                logEntryText.setText(logEntry);
                this.logGrid.add(logEntryText, 0, i);
                i++;
            }
        } catch(IOException e) {
            System.out.println("Error in RMI:");
            e.printStackTrace();
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        }
    }
    
}
