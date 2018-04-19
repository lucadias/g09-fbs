/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ARTICLE_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.RemoteArticleService;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author salzm
 */
public final class ArticleEditView implements FBSView {
    private ArticleDTO articleDTO;
    
    private TextField articleName;
    private TextField articleDescription;
    private TextField articleNumberValue;
    private TextField articleStockValue;
    private TextField articleMinStockValue;
    
    private RemoteArticleService articleService = null;
    
    public ArticleEditView() {
        try {
            final String url = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            this.articleService = (RemoteArticleService) Naming.lookup(url);
            ArticleDTO currentArticle = articleService.getById(1);
            this.articleDTO = currentArticle;
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            this.articleDTO = new ArticleDTO(Integer.MAX_VALUE);
            this.articleDTO.setDescription("Error in RMI: "+e);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    public HBox addHBoxTop() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button backButton = new Button("Zurück");
        backButton.setPrefSize(100, 20);

        Button saveButton = new Button("Speichern");
        saveButton.setPrefSize(100, 20);
        
        Button updateStockButton = new Button("Lager updaten");
        updateStockButton.setPrefSize(100, 20);
        
        hbox.getChildren().addAll(backButton, saveButton, updateStockButton);
        
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                saveArticle();
            }
        });

        return hbox;
    }
    
    public void saveArticle() {
        try {
            String hash = this.articleService.lock(this.articleDTO);
            this.articleDTO.setName(this.articleName.getText());
            this.articleDTO.setDescription(this.articleDescription.getText());
            this.articleDTO.setArticleNumber(Integer.valueOf(this.articleNumberValue.getText()));
            this.articleDTO.setInStock(Integer.valueOf(this.articleStockValue.getText()));
            this.articleDTO.setMinInStock(Integer.valueOf(this.articleMinStockValue.getText()));
            this.articleService.save(this.articleDTO, hash);
        } catch (RemoteException e) {
            this.articleDTO.setDescription("Error in RMI: "+e);
            System.out.println("Error in RMI: "+e);
        }
        
    }

    @Override
    public Node getBottomPane() {
        return new GridPane();
    }
    
    @Override
    public Node getCenterPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        
        grid.add(this.addHBoxTop(), 1, 0);
        
        this.articleName = new TextField();
        articleName.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        //ToDo: remove this check if is handled on lower level
        String name = this.articleDTO.getName() != null ? this.articleDTO.getName() : "";
        articleName.setText(name);
        grid.add(articleName, 1, 1);
        
        this.articleDescription = new TextField();
        articleDescription.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        //ToDo: remove this check if is handled on lower level
        String description = this.articleDTO.getDescription()!= null ? this.articleDTO.getDescription(): "";
        articleDescription.setText(description);
        grid.add(articleDescription, 1, 2, 1, 7);
        
        Text articleNumber = new Text("Artikelnummer");
        articleNumber.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(articleNumber, 2, 2);
        
        this.articleNumberValue = new TextField();
        articleNumberValue.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        articleNumberValue.setText(Integer.toString(this.articleDTO.getArticleNumber()));
        grid.add(articleNumberValue, 2, 3);
        
        Text articleStock = new Text("Lager");
        articleStock.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(articleStock, 2, 4);
        
        this.articleStockValue = new TextField();
        articleStockValue.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        articleStockValue.setText(Integer.toString(this.articleDTO.getInStock()));
        grid.add(articleStockValue, 2, 5);
        
        Text articleMinStock = new Text("Min-Bestellung");
        articleMinStock.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(articleMinStock, 2, 6);
        
        this.articleMinStockValue = new TextField();
        articleMinStockValue.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        articleMinStockValue.setText(Integer.toString(this.articleDTO.getMinInStock()));
        grid.add(articleMinStockValue, 2, 7);
        
        return grid;
    }
}
