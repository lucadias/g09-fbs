package ch.hslu.appe.fbs.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is the main class for the client application
 * @author joel salzmann
 */
public class Client extends Application implements RepaintRequestListener {
    
    public static final int REGISTRY_PORT = 1099;
    public static String SESSION = null;
    public static String username = "";
    private BorderPane border = new BorderPane();
    private Stage stage;
    private JavaFXViewController viewController;
    private Scene currentScene;
    
    /**
     * This method is called by javaFX directly after the main
     * This method initializes the viewController and asks him to provide content
     * for the diffrent areas of the gui.
     * @param primaryStage The stage in which the scenes will be placed
     */
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
    
    /**
     * This method is used to ask the viewController to provide new content
     * for the different areas of the gui
     */
    @Override
    public void repaint() {
        this.border.setLeft(viewController.getLeftPane());
        this.border.setTop(viewController.getTopPane());
        this.border.setCenter(viewController.getCenterPane());
    }

    /**
     * Main method to start the client
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
