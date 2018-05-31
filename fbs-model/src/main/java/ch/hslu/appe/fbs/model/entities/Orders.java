package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Orders")
public class Orders {
    private int idOrders;
    private int employeeIdEmployee;
    private Timestamp date;
    private Double totalPrice;
    private int orderStateIdOrderState;
    private Integer clientIdClients;

    @Id
    @Column(name = "idOrders", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(int idOrders) {
        this.idOrders = idOrders;
    }

    @Basic
    @Column(name = "Employee_idEmployee", nullable = false)
    public int getEmployeeIdEmployee() {
        return employeeIdEmployee;
    }

    public void setEmployeeIdEmployee(int employeeIdEmployee) {
        this.employeeIdEmployee = employeeIdEmployee;
    }

    @Basic
    @Column(name = "Date", nullable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "TotalPrice", nullable = true, precision = 0)
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Basic
    @Column(name = "OrderState_idOrderState", nullable = false)
    public int getOrderStateIdOrderState() {
        return orderStateIdOrderState;
    }

    public void setOrderStateIdOrderState(int orderStateIdOrderState) {
        this.orderStateIdOrderState = orderStateIdOrderState;
    }

    @Basic
    @Column(name = "Client_idClients", nullable = false)
    public Integer getClientIdClients() {
        return clientIdClients;
    }

    public void setClientIdClients(Integer clientIdClients) {
        this.clientIdClients = clientIdClients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return idOrders == orders.idOrders &&
                employeeIdEmployee == orders.employeeIdEmployee &&
                orderStateIdOrderState == orders.orderStateIdOrderState &&
                clientIdClients == orders.clientIdClients &&
                Objects.equals(date, orders.date) &&
                Objects.equals(totalPrice, orders.totalPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idOrders, employeeIdEmployee, date, totalPrice, orderStateIdOrderState, clientIdClients);
    }
}
