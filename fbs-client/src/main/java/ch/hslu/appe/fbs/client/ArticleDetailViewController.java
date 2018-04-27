package ch.hslu.appe.fbs.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author salzm
 */
public class ArticleDetailViewController implements Initializable {
    private ArticleDTO articleDTO;
    
    @FXML
    private Label articleName;
    
    @FXML
    private Label articleDescription;
    
    @FXML
    private Label articleNumber;
    
    @FXML
    private Label articleStock;
    
    @FXML
    private Label articleMinStock;
    
    @FXML
    public void showEditView(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/DashboardView.fxml"));
//            Parent dashboard = (Parent) loader.load();
//            DashboardViewController dashboardViewController = (DashboardViewController) loader.getController();
//            JavaFXViewController.getInstance().setView(dashboard);
//            JavaFXViewController.getInstance().repaint();
//        } catch (IOException e) {
//            System.out.println("Error loading fxml: "+e.getMessage());
//        }
    }
    
    @FXML
    public void showEditStock(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/DashboardView.fxml"));
//            Parent dashboard = (Parent) loader.load();
//            DashboardViewController dashboardViewController = (DashboardViewController) loader.getController();
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
            ArticleDTO currentArticle = articleService.getById(1);
            this.articleDTO = currentArticle;
            this.fillArticle();
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            this.articleDTO = new ArticleDTO(Integer.MAX_VALUE);
            this.articleDTO.setDescription("Error in RMI: "+e);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    private void fillArticle() {
        this.articleName.setText(this.articleDTO.getName());
        this.articleDescription.setText(this.articleDTO.getDescription());
        this.articleNumber.setText(String.valueOf(this.articleDTO.getArticleNumber()));
        this.articleStock.setText(String.valueOf(this.articleDTO.getInStock()));
        this.articleMinStock.setText(String.valueOf(this.articleDTO.getMinInStock()));
    }
    
}
