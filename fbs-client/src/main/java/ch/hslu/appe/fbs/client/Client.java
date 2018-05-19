/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author salzm
 */
public class Client extends Application implements RepaintRequestListener {
    
    public static final int REGISTRY_PORT = 1099;
    public static String SESSION = null;
    public static String username = "";
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
        repaint();
    }
    
    @Override
    public void repaint() {
        this.border.setLeft(viewController.getLeftPane());
        this.border.setTop(viewController.getTopPane());
        this.border.setCenter(viewController.getCenterPane());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
