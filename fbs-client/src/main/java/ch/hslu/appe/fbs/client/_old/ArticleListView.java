/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client._old;

import static ch.hslu.appe.fbs.client._old.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client._old.JavaFXViewController.ARTICLE_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.RemoteArticleService;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author salzm
 */
public final class ArticleListView implements FBSView {
    private List<ArticleDTO> articleDTOList;
    
    public ArticleListView() {
        try {
            final String url = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            final RemoteArticleService articleService = (RemoteArticleService) Naming.lookup(url);
            List<ArticleDTO> currentArticles = articleService.getList();
            this.articleDTOList = currentArticles;
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            this.articleDTOList = new ArrayList<ArticleDTO>();
            System.out.println("Error in RMI: "+e);
        }
    }

    @Override
    public Node getCenterPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10); 
        grid.setVgap(10); 
        grid.setPadding(new Insets(10, 10, 10, 10));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        Text title = new Text("Artikelliste");
        title.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        
        Button newArticleButton = new Button("Neuer Artikel");
        newArticleButton.setPrefSize(100, 20);
        
        hbox.getChildren().addAll(title, newArticleButton);
        
        grid.add(hbox, 1, 1, 5, 1);
        
        Text nameLabel = new Text("Name");
        nameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        Text numberLabel = new Text("Nr.");
        numberLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        Text priceLabel = new Text("Preis");
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        Text stockLabel = new Text("Lager");
        stockLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        Text emptyLabel = new Text("");
        emptyLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        
        grid.add(nameLabel, 1, 2);
        grid.add(numberLabel, 2, 2);
        grid.add(priceLabel, 3, 2);
        grid.add(stockLabel, 4, 2);
        grid.add(emptyLabel, 5, 2);
        
        int rowcount=3;
        
        for(ArticleDTO article:this.articleDTOList) {
            Text articleName = new Text(article.getName());
            articleName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            Text articleNumber = new Text(String.valueOf(article.getArticleNumber()));
            articleNumber.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            Text articlePrice = new Text(String.valueOf(article.getPrice()));
            articlePrice.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            Text articleStock = new Text(String.valueOf(article.getInStock()));
            articleStock.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            Button detailButton = new Button("Detail anzeigen");
            detailButton.setPrefSize(100, 20);
        
            grid.add(articleName, 1, rowcount);
            grid.add(articleNumber, 2, rowcount);
            grid.add(articlePrice, 3, rowcount);
            grid.add(articleStock, 4, rowcount);
            grid.add(detailButton, 5, rowcount);
            
            rowcount++;
        }
        
        return grid;
    }

    @Override
    public Node getBottomPane() {
        return new GridPane();
    }
    
}
