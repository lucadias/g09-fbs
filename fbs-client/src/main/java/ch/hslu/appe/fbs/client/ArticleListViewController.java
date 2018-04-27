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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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
        int i=1;
        System.out.println("till here it works");
        for(ArticleDTO article:this.articleList) {
            Label articleName = new Label();
            articleName.setText(article.getName());
            Label articleNumber = new Label();
            articleNumber.setText(String.valueOf(article.getArticleNumber()));
            Label articlePrice = new Label();
            articlePrice.setText(String.valueOf(article.getPrice()));
            Label articleStock = new Label();
            articleStock.setText(String.valueOf(article.getInStock()));
            Button details = new Button();
            details.setText("Details");
            this.articleGrid.add(details, 5, i);
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
