package ch.hslu.appe.fbs.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * JavaDoc
 */
public class JavaFXViewController extends Application {
    public static final int REGISTRY_PORT = 1099;
    public static final String ARTICLE_SERVICE_NAME = "ArticleService";

    public JavaFXViewController() {
        
    }
    
    public static void main(final String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Artikel Detailansicht");
        ArticleDetailView detail = new ArticleDetailView();
        BorderPane border = new BorderPane();
        HBox hboxTop = detail.addHBoxTop();
        GridPane grid = detail.addGridPaneCenter();
        border.setTop(hboxTop);
        border.setCenter(grid);
        
        Scene scene = new Scene(border, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
