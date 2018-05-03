/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.Client.SESSION;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ARTICLE_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ORDER_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.RemoteArticleService;
import ch.hslu.appe.fbs.remote.RemoteOrderService;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author salzm
 */
public class OrderEditViewController implements Initializable {
    private RemoteOrderService orderService = null;
    private RemoteArticleService articleService = null;
    private List<OrderedArticleDTO> orderedArticleList;
    private List<ArticleDTO> allArticleList;
    private OrderDTO orderDTO;
    private int orderId;
    
    @FXML
    private Label orderNumber;
    
    @FXML
    private Label orderDate;
    
    @FXML
    private ChoiceBox orderChoice;
    
    @FXML
    private ChoiceBox employeeChoice;
    
    @FXML
    private ChoiceBox clientChoice;
    
    @FXML
    private GridPane orderedArticleGrid;
    
    @FXML
    private GridPane allArticleGrid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final String urlStringOrder = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ORDER_SERVICE_NAME;
            this.orderService = (RemoteOrderService) Naming.lookup(urlStringOrder);
            final String urlStringArticle = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            this.articleService = (RemoteArticleService) Naming.lookup(urlStringArticle);
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            this.orderDTO = new OrderDTO(Integer.MAX_VALUE);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    public void setId(int id) {
        this.orderId = id;
        try {
            if(this.orderId != -1) {
                OrderDTO currentOrder = orderService.getById(SESSION, this.orderId);
                this.orderDTO = currentOrder;
                String date = String.valueOf(this.orderDTO.getDate());
                this.orderDate.setText(date);
                String number = String.valueOf(this.orderDTO.getId());
                this.orderNumber.setText(number);
                //ToDo: Check Nullpointers, maybe work with optionals
                this.orderedArticleList = this.orderDTO.getOrderedArticleDTOList();
                this.fillOrderedArticles();
                this.fillAllArticles();
            } else {
                this.orderDTO = new OrderDTO(-1);
            }
        } catch(RemoteException e) {
            this.orderDTO = new OrderDTO(Integer.MAX_VALUE);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    private void fillOrderedArticles() {
        int i = 1;
        for(OrderedArticleDTO orderedArticle:this.orderedArticleList) {
            ArticleDTO article = orderedArticle.getArticleDTO();
            Text articleName = new Text(article.getName());
            int amount = orderedArticle.getAmount();
            Text articleAmount = new Text(String.valueOf(amount));
            double price = article.getPrice();
            Text articlePrice = new Text(String.valueOf(price));
            Button removeFromOrderButton = new Button();
            removeFromOrderButton.setText("entfernen");
            removeFromOrderButton.setPrefWidth(100);
            removeFromOrderButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    removeFromOrder(orderedArticle);
                }
            });
            this.orderedArticleGrid.add(articleName, 0, i);
            this.orderedArticleGrid.add(articleAmount, 1, i);
            this.orderedArticleGrid.add(articlePrice, 2, i);
            this.orderedArticleGrid.add(removeFromOrderButton, 3, i);
            i++;
            System.out.println("article in order:"+article.getName());
        }
    }
    
    private void fillAllArticles() {
        int i = 1;
        try {
            this.allArticleList = this.articleService.getList(SESSION);
            for(ArticleDTO article:this.allArticleList) {
                Text articleName = new Text(article.getName());
                TextField articleAmount = new TextField();
                double price = article.getPrice();
                Text articlePrice = new Text(String.valueOf(price));
                Button addToOrderButton = new Button();
                addToOrderButton.setText("hinzufügen");
                addToOrderButton.setPrefWidth(100);
                addToOrderButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        int amount = Integer.valueOf(articleAmount.getText());
                        addToOrder(article, amount);
                    }
                });
                this.orderedArticleGrid.add(articleName, 0, i);
                this.orderedArticleGrid.add(articleAmount, 1, i);
                this.orderedArticleGrid.add(articlePrice, 2, i);
                this.orderedArticleGrid.add(addToOrderButton, 3, i);
                i++;
            }
        } catch(RemoteException e) {
            System.out.println("Error in RMI:"+e.getMessage());
        }
    }
    
    private void addToOrder(ArticleDTO article, int amount) {
        for(int i=0; i<amount; i++) {
            OrderedArticleDTO orderedArticle = new OrderedArticleDTO(-1);
            orderedArticle.setAmount(amount);
            orderedArticle.setArticleDTO(article);
            this.orderedArticleList.add(orderedArticle);
        }
    }
    
    private void removeFromOrder(OrderedArticleDTO article) {
        this.orderedArticleList.remove(article);
    }
}
