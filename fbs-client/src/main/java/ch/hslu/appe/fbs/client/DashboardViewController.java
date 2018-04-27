/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author salzm
 */
public class DashboardViewController implements Initializable {
    
    private ArrayList<StateChangeListener> stateChangeListeners = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void addStateChangeListener(StateChangeListener listener) {
        if(!this.stateChangeListeners.contains(listener)) {
            this.stateChangeListeners.add(listener);
        }
    }    
}
