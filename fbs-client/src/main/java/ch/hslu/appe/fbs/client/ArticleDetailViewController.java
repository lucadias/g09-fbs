package ch.hslu.appe.fbs.client;

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.Client.SESSION;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ARTICLE_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteArticleService;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 * This class is responsible for the articleDetailView and defines the methods for that view
 * @author joel salzmann
 */
public class ArticleDetailViewController implements Initializable {
    private ArticleDTO articleDTO;
    private int articleId=1;
    
    private RemoteArticleService articleService = null;
    
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
    private Label articlePrice;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button editButton;
    
    @FXML
    private Button editStockButton;
    
    @FXML
    public void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ArticleListView.fxml"));
            Parent articleList = (Parent) loader.load();
            ArticleListViewController articleListViewController = (ArticleListViewController) loader.getController();
            JavaFXViewController.getInstance().setView(articleList);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }
    
    @FXML
    public void showEditView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ArticleEditView.fxml"));
            Parent articleEdit = (Parent) loader.load();
            ArticleEditViewController articleEditViewController = (ArticleEditViewController) loader.getController();
            articleEditViewController.setId(this.articleId);
            JavaFXViewController.getInstance().setView(articleEdit);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
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
            ArticleDTO currentArticle = articleService.getById(SESSION, this.articleId);
            this.articleDTO = currentArticle;
            this.fillArticle();
        } catch(RemoteException e) {
            this.articleDTO = new ArticleDTO(Integer.MAX_VALUE);
            this.articleDTO.setDescription("Error in RMI: "+e);
            System.out.println("Error in RMI: "+e);
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        }
    }
    
    private void fillArticle() {
        this.articleName.setText(this.articleDTO.getName());
        this.articleDescription.setText(this.articleDTO.getDescription());
        this.articleNumber.setText(String.valueOf(this.articleDTO.getArticleNumber()));
        this.articleStock.setText(String.valueOf(this.articleDTO.getInStock()));
        this.articleMinStock.setText(String.valueOf(this.articleDTO.getMinInStock()));
        this.articlePrice.setText(String.valueOf(this.articleDTO.getPrice()));
    }
    
}
