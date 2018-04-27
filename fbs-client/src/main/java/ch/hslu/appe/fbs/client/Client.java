/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author salzm
 */
public class Client extends Application implements RepaintRequestListener {
    
    private BorderPane border = new BorderPane();
    private Stage stage;
    private JavaFXViewController viewController;
    private Scene currentScene;
    
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.viewController = JavaFXViewController.getInstance();
        viewController.addRepaintRequestListener(this);
        viewController.start();
        primaryStage.setTitle("Filialbestellsystem");
        this.border.setLeft(viewController.getLeftPane());
        this.border.setTop(viewController.getTopPane());
        this.border.setCenter(viewController.getCenterPane());
        this.currentScene = new Scene(border, 800, 500);
        this.stage.setScene(this.currentScene);
        this.stage.show();
        //ToDo: Check if is logged in
        //ToDo: outsource scene and panefilling
        repaint();
    }
    
    @Override
    public void repaint() {
        this.border.setLeft(viewController.getLeftPane());
        this.border.setTop(viewController.getTopPane());
        this.border.setCenter(viewController.getCenterPane());
//        this.border.getCenter().getScene().setRoot(this.currentScene);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
