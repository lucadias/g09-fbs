/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.Client.SESSION;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ORDER_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.RemoteOrderService;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author salzm
 */
public class OrderDetailViewController implements Initializable {
    
    private RemoteOrderService orderService = null;
    private List<OrderedArticleDTO> articleList;
    private OrderDTO orderDTO;
    private int orderId;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button editButton;
    
    @FXML
    private GridPane articleGrid;
    
    @FXML
    public void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderListView.fxml"));
            Parent orderList = (Parent) loader.load();
            OrderListViewController orderListViewController = (OrderListViewController) loader.getController();
            JavaFXViewController.getInstance().setView(orderList);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }
    
    @FXML
    public void showEditView(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/OrderEditView.fxml"));
//            Parent orderEdit = (Parent) loader.load();
//            OrderEditViewController orderEditViewController = (OrderEditViewController) loader.getController();
//            orderEditViewController.setId(this.orderId);
//            JavaFXViewController.getInstance().setView(orderEdit);
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
            final String urlString = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ORDER_SERVICE_NAME;
            this.orderService = (RemoteOrderService) Naming.lookup(urlString);
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            this.orderDTO = new OrderDTO(Integer.MAX_VALUE);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    public void setId(int id) {
        this.orderId = id;
        try {
            OrderDTO currentOrder = orderService.getById(SESSION, this.orderId);
            this.orderDTO = currentOrder;
            //ToDo: Check Nullpointers, maybe work with optionals
            this.articleList = this.orderDTO.getOrderedArticleDTOList();
            this.fillOrder();
        } catch(RemoteException e) {
            this.orderDTO = new OrderDTO(Integer.MAX_VALUE);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    private void fillOrder() {
        int i = 1;
        for(OrderedArticleDTO orderedArticle:this.articleList) {
            ArticleDTO article = orderedArticle.getArticleDTO();
            Text articleName = new Text(article.getName());
            int amount = orderedArticle.getAmount();
            Text articleAmount = new Text(String.valueOf(amount));
            double price = article.getPrice();
            Text articlePrice = new Text(String.valueOf(price));
            double total = orderedArticle.getTotalPrice();
            Text articleTotal = new Text(String.valueOf(total));
            this.articleGrid.add(articleName, 0, i);
            this.articleGrid.add(articleAmount, 1, i);
            this.articleGrid.add(articlePrice, 2, i);
            this.articleGrid.add(articleTotal, 3, i);
            i++;
            System.out.println("article in order:"+article.getName());
        }
    }
    
}
