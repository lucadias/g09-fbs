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
public final class Client extends Application {
    public static final int REGISTRY_PORT = 1099;
    
    public static void main(final String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        JavaFXViewController viewController = new JavaFXViewController();
        viewController.start();
        primaryStage.setTitle("Filialbestellsystem");
        BorderPane border = new BorderPane();
//        border.setTop(viewController.getTopPane());
        border.setCenter(viewController.getCenterPane());
//        border.setRight(viewController.getRightPane());
//        border.setLeft(viewController.getLeftPane());
        border.setBottom(viewController.getBottomPane());
        
        Scene scene = new Scene(border, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
