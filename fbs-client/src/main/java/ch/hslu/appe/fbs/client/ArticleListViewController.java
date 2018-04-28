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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author salzm
 */
public class ArticleListViewController implements Initializable {
    
    private ArrayList<ArticleDTO> articleList;
    
    @FXML
    private GridPane articleGrid;
    
    @FXML
    private Button newArticleButton;
    
    @FXML
    public void addNewArticle(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/ArticleDetailView.fxml"));
//            Parent dashboard = (Parent) loader.load();
//            ArticleDetailViewController articleDetailViewController = (ArticleDetailViewController) loader.getController();
//            articleDetailViewController.setId(3);
//            JavaFXViewController.getInstance().setView(dashboard);
//            JavaFXViewController.getInstance().repaint();
//        } catch (IOException e) {
//            System.out.println("Error loading fxml: "+e.getMessage());
//        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final String urlString = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            final RemoteArticleService articleService = (RemoteArticleService) Naming.lookup(urlString);
            ArrayList<ArticleDTO> currentArticles = (ArrayList<ArticleDTO>) articleService.getList();
            this.articleList = currentArticles;
            this.fillList();
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            System.out.println("Error in RMI: "+e);
        }
    }
    
    private void fillList() {
        int i=0;
        for(ArticleDTO article:this.articleList) {
            Label articleName = new Label(article.getName());
            articleName.setFont(new Font("Arial", 18));
            articleName.setPrefWidth(150);
            Label articleNumber = new Label(String.valueOf(article.getArticleNumber()));
            articleNumber.setFont(new Font("Arial", 18));
            articleNumber.setPrefWidth(100);
            Label articlePrice = new Label(String.valueOf(article.getPrice()));
            articlePrice.setFont(new Font("Arial", 18));
            articlePrice.setPrefWidth(75);
            Label articleStock = new Label(String.valueOf(article.getInStock()));
            articleStock.setFont(new Font("Arial", 18));
            articleStock.setPrefWidth(75);
            Button details = new Button();
            details.setText("Details");
            details.setPrefWidth(100);
            details.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    showDetail(article.getId());
                }
            });
            this.articleGrid.add(articleName, 0, i);
            this.articleGrid.add(articleNumber, 1, i);
            this.articleGrid.add(articlePrice, 2, i);
            this.articleGrid.add(articleStock, 3, i);
            this.articleGrid.add(details, 4, i);
            i++;
            System.out.println("new article: "+article.getName());
        }
    }
    
    private void showDetail(int id) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ArticleDetailView.fxml"));
            Parent dashboard = (Parent) loader.load();
            ArticleDetailViewController articleDetailViewController = (ArticleDetailViewController) loader.getController();
            articleDetailViewController.setId(id);
            JavaFXViewController.getInstance().setView(dashboard);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }
}
