package ch.hslu.appe.fbs.client;

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.Client.SESSION;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ORDER_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteOrderService;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 * This class is responsible for the orderDetailView and defines the methods for that view
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
    private Label orderNumber;
    
    @FXML
    private Label orderDate;
    
    @FXML
    private Label orderState;
    
    @FXML
    private Label orderEmployee;
    
    @FXML
    private Label orderClient;
    
    /**
     * This method is called when the GUI-Button "zurück" is pressed.
     * This method navigates to the orderListView
     * @param event An ActionEvent given by JavaFx
     */
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
    
    /**
     * This method is called when the GUI-Button "Bearbeiten" is pressed
     * This method navigates to the orderEditView and sets the orderDTO to be edited
     * @param event An ActionEvent given by JavaFx
     */
    @FXML
    public void showEditView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderEditView.fxml"));
            Parent orderEdit = (Parent) loader.load();
            OrderEditViewController orderEditViewController = (OrderEditViewController) loader.getController();
            boolean success = orderEditViewController.setId(this.orderId);
            if(success) {
                JavaFXViewController.getInstance().setView(orderEdit);
                JavaFXViewController.getInstance().repaint();
            }
        } catch (IOException e) {
            System.out.println("Error loading fxml: "+e.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     * This method is called when the orderDetailView Fxml file is loaded
     * This method initializes the orderService
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
    
    /**
     * This method is used to set the orderDTO given by id.
     * Then it fills the ui components with the orderDTO's values
     * @param id the id of the orderDTO
     */
    public void setId(int id) {
        this.orderId = id;
        try {
            OrderDTO currentOrder = orderService.getById(SESSION, this.orderId);
            this.orderDTO = currentOrder;
            String date = String.valueOf(this.orderDTO.getDate());
            this.orderDate.setText(date);
            String employee = this.orderDTO.getEmployeeDTO().getUsername();
            this.orderEmployee.setText(employee);
            String number = String.valueOf(this.orderDTO.getId());
            this.orderNumber.setText(number);
            String state = this.orderDTO.getOrderStateDTO().getState();
            this.orderState.setText(state);
            String client = this.orderDTO.getClientDTO().getFirstname() + " " + this.orderDTO.getClientDTO().getSurname();
            this.orderClient.setText(client);
            //ToDo: Check Nullpointers, maybe work with optionals
            this.articleList = this.orderDTO.getOrderedArticleDTOList();
            this.fillOrder();
        } catch(RemoteException e) {
            this.orderDTO = new OrderDTO(Integer.MAX_VALUE);
            System.out.println("Error in RMI: "+e);
        } catch(UserNotLoggedInException e){
            System.out.println("User is not logged in");
        }
    }
    
    /**
     * This method fills the grid with the orderedArticles
     */
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
