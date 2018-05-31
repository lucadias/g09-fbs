package ch.hslu.appe.fbs.client;

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.Client.SESSION;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ORDER_SERVICE_NAME;

import ch.hslu.appe.fbs.remote.remoteServices.RemoteOrderService;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
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

/**
 * FXML Controller class
 * This class is responsible for the orderListView and defines the methods for that view
 * @author joel salzmann
 */
public class OrderListViewController implements Initializable {
    
    private RemoteOrderService orderService = null;
    private ArrayList<OrderDTO> orderList;
    
    @FXML
    private GridPane orderGrid;
    
    @FXML
    private Button newOrderButton;
    
    @FXML
    public void newOrder(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderEditView.fxml"));
            Parent orderEdit = (Parent) loader.load();
            OrderEditViewController orderEditViewController = (OrderEditViewController) loader.getController();
            orderEditViewController.setId(-1);
            JavaFXViewController.getInstance().setView(orderEdit);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: ");
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final String urlString = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ORDER_SERVICE_NAME;
            this.orderService = (RemoteOrderService) Naming.lookup(urlString);
            ArrayList<OrderDTO> currentOrders = (ArrayList<OrderDTO>) this.orderService.getList(SESSION);
            this.orderList = currentOrders;
            this.fillList();
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            System.out.println("Error in RMI: ");
            e.printStackTrace();
        } catch(UserNotLoggedInException e){
            System.out.println("User is not logged in");
        }
    }    
    
    private void fillList() {
        int i=1;
        for(OrderDTO order:this.orderList) {
            Label orderNumber = new Label(String.valueOf(order.getId()));
            orderNumber.setFont(new Font("Arial", 18));
            orderNumber.setPrefWidth(80);
            if(order.getClientDTO() != null) {
                String clientName = order.getClientDTO().getFirstname() + " " + order.getClientDTO().getSurname();
                Label client = new Label(String.valueOf(clientName));
                client.setFont(new Font("Arial", 18));
                client.setPrefWidth(130);
                this.orderGrid.add(client, 1, i);
            }
            if(order.getEmployeeDTO() != null) {
                String employeeName = order.getEmployeeDTO().getUsername();
                Label employee = new Label(String.valueOf(employeeName));
                employee.setFont(new Font("Arial", 18));
                employee.setPrefWidth(130);
                this.orderGrid.add(employee, 2, i);
            }
            if(order.getOrderStateDTO() != null) {
                String orderState = order.getOrderStateDTO().getState();
                Label state = new Label(String.valueOf(orderState));
                state.setFont(new Font("Arial", 18));
                state.setPrefWidth(100);
                this.orderGrid.add(state, 3, i);
            }
            Button details = new Button();
            details.setText("Details");
            details.setPrefWidth(100);
            details.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    showDetail(order.getId());
                    System.out.println(order.getId());
                }
            });
            this.orderGrid.add(orderNumber, 0, i);
            this.orderGrid.add(details, 4, i);
            i++;
            System.out.println("new order: "+order.getId());
        }
    }
    
    private void showDetail(int id) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderDetailView.fxml"));
            Parent order = (Parent) loader.load();
            OrderDetailViewController orderDetailViewController = (OrderDetailViewController) loader.getController();
            orderDetailViewController.setId(id);
            JavaFXViewController.getInstance().setView(order);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: ");
            e.printStackTrace();
        }
    }
    
}
