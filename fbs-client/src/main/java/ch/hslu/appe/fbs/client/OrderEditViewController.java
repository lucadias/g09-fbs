/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.Client.SESSION;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ARTICLE_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.CLIENT_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.EMPLOYEE_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ORDERSTATE_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ORDER_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteArticleService;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteOrderService;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteClientService;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteEmployeeService;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteOrderStateService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private RemoteEmployeeService employeeService = null;
    private RemoteOrderStateService orderStateService = null;
    private RemoteClientService clientService = null;
    private List<OrderStateDTO> orderStateList;
    private List<ClientDTO> clientList;
    private List<EmployeeDTO> employeeList;
    private List<OrderedArticleDTO> orderedArticleList;
    private List<ArticleDTO> allArticleList;
    private List<ArticleDTO> searchArticleList;
    private OrderDTO orderDTO;
    private int orderId;
    
    @FXML
    private Label orderNumber;
    
    @FXML
    private Label orderDate;
    
    @FXML
    private ChoiceBox employeeChoice;
    
    @FXML
    private ChoiceBox clientChoice;
    
    @FXML
    private ChoiceBox stateChoice;
    
    @FXML
    private GridPane orderedArticleGrid;
    
    @FXML
    private GridPane allArticleGrid;
    
    @FXML
    private GridPane searchArticleGrid;

    @FXML
    private TextField searchInput;
    
    @FXML
    public void back(ActionEvent event) {
        if(this.orderId != -1) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/OrderDetailView.fxml"));
                Parent orderDetail = (Parent) loader.load();
                OrderDetailViewController orderDetailViewController = (OrderDetailViewController) loader.getController();
                orderDetailViewController.setId(this.orderId);
                JavaFXViewController.getInstance().setView(orderDetail);
                JavaFXViewController.getInstance().repaint();
            } catch (IOException e) {
                System.out.println("Error loading fxml: "+e.getMessage());
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/OrderListView.fxml"));
                Parent orders = (Parent) loader.load();
                OrderListViewController orderListViewController = (OrderListViewController) loader.getController();
                JavaFXViewController.getInstance().setView(orders);
                JavaFXViewController.getInstance().repaint();
            } catch (IOException e) {
                System.out.println("Error loading fxml: "+e.getMessage());
            }
        }
    }
    
    @FXML
    public void save(ActionEvent event) {
        //ToDo: set state, employee and client for orderDTO before saving
        OrderStateDTO state = (OrderStateDTO)this.stateChoice.getValue();
        this.orderDTO.setOrderStateDTO(state);
        try {
            String hash = this.orderService.lock(SESSION, this.orderDTO);
            FBSFeedback feedback = this.orderService.save(SESSION, this.orderDTO, hash);
            feedback = this.orderService.release(SESSION, this.orderDTO, hash);
        } catch(RemoteException e) {
            System.out.println("Error while saving: "+e.getMessage());
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderDetailView.fxml"));
            Parent orderDetail = (Parent) loader.load();
            OrderDetailViewController orderDetailViewController = (OrderDetailViewController) loader.getController();
            orderDetailViewController.setId(this.orderId);
            JavaFXViewController.getInstance().setView(orderDetail);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }
    
    @FXML
    public void searchArticle(ActionEvent event) {
        try {
            String searchString = this.searchInput.getText();
            this.searchArticleList = this.articleService.search(SESSION, searchString);
            this.fillSearchedArticles();
        } catch(RemoteException e) {
            System.out.println("Error in search: "+e.getMessage());
        }
    }
    
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
            final String urlStringEmployee = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + EMPLOYEE_SERVICE_NAME;
            this.employeeService = (RemoteEmployeeService) Naming.lookup(urlStringEmployee);
            final String urlStringOrderState = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ORDERSTATE_SERVICE_NAME;
            this.orderStateService = (RemoteOrderStateService) Naming.lookup(urlStringOrderState);
            final String urlStringClient = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + CLIENT_SERVICE_NAME;
            this.clientService = (RemoteClientService) Naming.lookup(urlStringClient);
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
                this.fillStateChoice();
                this.fillClientChoice();
                this.fillEmployeeChoice();
                //ToDo: Check Nullpointers, maybe work with optionals
                this.orderedArticleList = this.orderDTO.getOrderedArticleDTOList();
            } else {
                this.orderDTO = new OrderDTO(-1);
                this.orderedArticleList = new ArrayList<>();
            }
            this.fillOrderedArticles();
            this.fillAllArticles();
        } catch(RemoteException e) {
            this.orderDTO = new OrderDTO(Integer.MAX_VALUE);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    private void fillStateChoice() {
        try {
            this.orderStateList = this.orderStateService.getList(SESSION);
            ObservableList<OrderStateDTO> stateObservList = FXCollections.observableArrayList(this.orderStateList);
            this.stateChoice.setItems(stateObservList);
            if(stateObservList.contains(this.orderDTO.getOrderStateDTO())) {
                this.stateChoice.setValue(this.orderDTO.getOrderStateDTO());
            }
        } catch(RemoteException e) {
            System.out.println("Error in getting orderStates:"+e.getMessage());
        }
    }
    
    private void fillClientChoice() {
        try {
            this.clientList = this.clientService.getList(SESSION);
            ObservableList<ClientDTO> clientObservList = FXCollections.observableArrayList(this.clientList);
            this.clientChoice.setItems(clientObservList);
            if(clientList.contains(this.orderDTO.getClientDTO())) {
                this.clientChoice.setValue(this.orderDTO.getClientDTO());
            }
        } catch(RemoteException e) {
            System.out.println("Error in getting orderStates:"+e.getMessage());
        }
    }
    
    private void fillEmployeeChoice() {
        try {
            this.employeeList = this.employeeService.getList(SESSION);
            ObservableList<EmployeeDTO> employeeObservList = FXCollections.observableArrayList(this.employeeList);
            this.employeeChoice.setItems(employeeObservList);
            if(employeeObservList.contains(this.orderDTO.getEmployeeDTO())) {
                this.employeeChoice.setValue(this.orderDTO.getEmployeeDTO());
            }
        } catch(RemoteException e) {
            System.out.println("Error in getting orderStates:"+e.getMessage());
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
                this.allArticleGrid.add(articleName, 0, i);
                this.allArticleGrid.add(articleAmount, 1, i);
                this.allArticleGrid.add(articlePrice, 2, i);
                this.allArticleGrid.add(addToOrderButton, 3, i);
                i++;
            }
        } catch(RemoteException e) {
            System.out.println("Error in RMI:"+e.getMessage());
        }
    }
    
    private void fillSearchedArticles() {
        int i = 1;
        for(ArticleDTO article:this.searchArticleList) {
            Text articleName = new Text(article.getName());
            System.out.println("found: "+articleName);
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
            this.searchArticleGrid.add(articleName, 0, i);
            this.searchArticleGrid.add(articleAmount, 1, i);
            this.searchArticleGrid.add(articlePrice, 2, i);
            this.searchArticleGrid.add(addToOrderButton, 3, i);
            i++;
        }
    }
    
    private void addToOrder(ArticleDTO article, int amount) {
        for(int i=0; i<amount; i++) {
            OrderedArticleDTO orderedArticle = new OrderedArticleDTO(-1);
            orderedArticle.setAmount(amount);
            orderedArticle.setArticleDTO(article);
            this.orderedArticleList.add(orderedArticle);
            this.orderDTO.setOrderedArticleDTOList(this.orderedArticleList);
        }
        try {
            String hash = this.orderService.lock(SESSION, this.orderDTO);
            System.out.println(hash);
            FBSFeedback feedback = this.orderService.save(SESSION, this.orderDTO, hash);
            System.out.println(feedback+" "+hash);
            feedback = this.orderService.release(SESSION, this.orderDTO, hash);
            this.refresh();
        } catch(RemoteException e) {
            System.out.println("Error in RMI:"+e.getMessage());
        }
    }
    
    private void removeFromOrder(OrderedArticleDTO article) {
        this.orderedArticleList.remove(article);
        this.orderDTO.setOrderedArticleDTOList(this.orderedArticleList);
        try {
            String hash = this.orderService.lock(SESSION, this.orderDTO);
            FBSFeedback feedback = this.orderService.save(SESSION, this.orderDTO, hash);
            System.out.println(feedback+" "+hash);
            feedback = this.orderService.release(SESSION, this.orderDTO, hash);
            System.out.println(feedback+" "+hash);
            this.refresh();
        } catch(RemoteException e) {
            System.out.println("Error in RMI:"+e.getMessage());
        }
    }
    
    private void refresh() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderEditView.fxml"));
            Parent orderEdit = (Parent) loader.load();
            OrderEditViewController orderEditViewController = (OrderEditViewController) loader.getController();
            orderEditViewController.setId(this.orderId);
            JavaFXViewController.getInstance().setView(orderEdit);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }
}
