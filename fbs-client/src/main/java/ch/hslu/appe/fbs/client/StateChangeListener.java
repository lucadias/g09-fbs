/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

import javafx.scene.Parent;

/**
 *
 * @author salzm
 */
public interface StateChangeListener {
    public void stateChanged(Parent event, Object controller);
}
