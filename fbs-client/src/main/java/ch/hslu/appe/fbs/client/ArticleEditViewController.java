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
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author salzm
 */
public class ArticleEditViewController implements Initializable {
    private ArticleDTO articleDTO;
    private int articleId=-1;
    
    private RemoteArticleService articleService = null;
    
    @FXML
    private TextField articleNameValue;
    
    @FXML
    private TextArea articleDescriptionValue;
    
    @FXML
    private TextField articleNumberValue;
    
    @FXML
    private TextField articleStockValue;
    
    @FXML
    private TextField articleMinStockValue;
    
    @FXML
    private TextField articlePriceValue;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button saveButton;
    
    @FXML
    public void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ArticleDetailView.fxml"));
            Parent dashboard = (Parent) loader.load();
            ArticleDetailViewController articleDetailViewController = (ArticleDetailViewController) loader.getController();
            articleDetailViewController.setId(this.articleId);
            JavaFXViewController.getInstance().setView(dashboard);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }
    
    @FXML
    public void save(ActionEvent event) {
        //ToDo: check values bevor saving
        try {
            String hash = this.articleService.lock(this.articleDTO);
            this.articleDTO.setName(this.articleNameValue.getText());
            this.articleDTO.setDescription(this.articleDescriptionValue.getText());
            this.articleDTO.setArticleNumber(Integer.valueOf(this.articleNumberValue.getText()));
            this.articleDTO.setInStock(Integer.valueOf(this.articleStockValue.getText()));
            this.articleDTO.setMinInStock(Integer.valueOf(this.articleMinStockValue.getText()));
            this.articleDTO.setPrice(Double.valueOf(this.articlePriceValue.getText()));
            this.articleService.save(this.articleDTO, hash);
            this.articleService.release(this.articleDTO, hash);
        } catch (RemoteException e) {
            this.articleDTO.setDescription("Error in RMI: "+e);
            System.out.println("Error in RMI: "+e);
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ArticleDetailView.fxml"));
            Parent dashboard = (Parent) loader.load();
            ArticleDetailViewController articleDetailViewController = (ArticleDetailViewController) loader.getController();
            articleDetailViewController.setId(this.articleId);
            JavaFXViewController.getInstance().setView(dashboard);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final String urlString = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            this.articleService = (RemoteArticleService) Naming.lookup(urlString);
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            this.articleDTO = new ArticleDTO(Integer.MAX_VALUE);
            this.articleDTO.setDescription("Error in RMI: "+e);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    public void setId(int id) {
        this.articleId = id;
        try {
            if(this.articleId != -1) {
                ArticleDTO currentArticle = this.articleService.getById(this.articleId);
                this.articleDTO = currentArticle;
                this.fillArticle();
            } else {
                this.articleDTO = new ArticleDTO(-1);
            }
        } catch(RemoteException e) {
            this.articleDTO = new ArticleDTO(Integer.MAX_VALUE);
            this.articleDTO.setDescription("Error in RMI: "+e);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    private void fillArticle() {
        this.articleNameValue.setText(this.articleDTO.getName());
        this.articleDescriptionValue.setText(this.articleDTO.getDescription());
        this.articleNumberValue.setText(String.valueOf(this.articleDTO.getArticleNumber()));
        this.articleStockValue.setText(String.valueOf(this.articleDTO.getInStock()));
        this.articleMinStockValue.setText(String.valueOf(this.articleDTO.getMinInStock()));
        this.articlePriceValue.setText(String.valueOf(this.articleDTO.getPrice()));
    }
    
}
