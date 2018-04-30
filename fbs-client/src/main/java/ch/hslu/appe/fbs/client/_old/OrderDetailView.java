/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client._old;

import static ch.hslu.appe.fbs.client._old.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client._old.JavaFXViewController.ORDER_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.RemoteOrderService;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author salzm
 */
public class OrderDetailView implements FBSView {
    private OrderDTO orderDTO;
    
    public OrderDetailView() {
        try {
            final String url = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ORDER_SERVICE_NAME;
            final RemoteOrderService orderService = (RemoteOrderService) Naming.lookup(url);
            OrderDTO currentOrder = orderService.getById(1);
            this.orderDTO = currentOrder;
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            this.orderDTO = new OrderDTO(Integer.MAX_VALUE);
            System.out.println("Error in RMI: "+e);
        }
    }

    @Override
    public Node getCenterPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10); 
        grid.setVgap(10); 
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        Text orderNumber = new Text("Order: "+this.orderDTO.getId());
        orderNumber.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(orderNumber, 1, 1);
        
        Text orderState = new Text(this.orderDTO.getOrderStateDTO().getState());
        orderState.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(orderState, 2, 1);
        
        Text employee = new Text(this.orderDTO.getEmployeeDTO().getUsername());
        employee.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(employee, 3, 1);
        
        Text client = new Text(this.orderDTO.getClientDTO().getFirstname() + " " + this.orderDTO.getClientDTO().getSurname());
        client.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(client, 1, 2, 2, 1);
        
        Text time = new Text(String.valueOf(this.orderDTO.getDate()));
        time.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(time, 3, 2, 2, 1);
        
        Text articleLabel = new Text("Artikel");
        articleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(articleLabel, 1, 3);
        
        Text amoutLabel = new Text("Artikel");
        amoutLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(amoutLabel, 2, 3);
        
        Text priceLabel = new Text("Preis");
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(priceLabel, 3, 3);
        
        Text totalPriceLabel = new Text("Total");
        totalPriceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(totalPriceLabel, 4, 3);
        
        int rowcount=4;
        for(OrderedArticleDTO article:this.orderDTO.getOrderedArticleDTOList()) {
            Text articleName = new Text(String.valueOf(article.getId()));
            articleName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            grid.add(articleName, 1, rowcount);

            Text amout = new Text(String.valueOf(article.getAmount()));
            amout.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            grid.add(amout, 2, rowcount);

            Text totalPrice = new Text(String.valueOf(article.getTotalPrice()));
            totalPrice.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            grid.add(totalPrice, 4, rowcount);
            
            rowcount++;
        }
        
        return grid;
    }

    @Override
    public Node getBottomPane() {
        return new GridPane();
    }
    
    
}
