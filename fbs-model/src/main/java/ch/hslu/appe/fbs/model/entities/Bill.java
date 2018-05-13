package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Bill")
public class Bill {
    private int idBill;
    private int clientIdClients;
    private Integer total;
    private int ordersIdOrders;
    private Date date;
    private Date datePaid;
    private Date dateDue;

    @Id
    @Column(name = "idBill", nullable = false)
    @GeneratedValue()
    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    @Basic
    @Column(name = "Client_idClients", nullable = false)
    public int getClientIdClients() {
        return clientIdClients;
    }

    public void setClientIdClients(int clientIdClients) {
        this.clientIdClients = clientIdClients;
    }

    @Basic
    @Column(name = "Total", nullable = true, precision = 0)
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Basic
    @Column(name = "Orders_idOrders", nullable = false)
    public int getOrdersIdOrders() {
        return ordersIdOrders;
    }

    public void setOrdersIdOrders(int ordersIdOrders) {
        this.ordersIdOrders = ordersIdOrders;
    }

    @Basic
    @Column(name = "Date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "DatePaid", nullable = true)
    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    @Basic
    @Column(name = "DateDue", nullable = true)
    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return idBill == bill.idBill &&
                clientIdClients == bill.clientIdClients &&
                ordersIdOrders == bill.ordersIdOrders &&
                Objects.equals(total, bill.total) &&
                Objects.equals(date, bill.date) &&
                Objects.equals(datePaid, bill.datePaid) &&
                Objects.equals(dateDue, bill.dateDue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idBill, clientIdClients, total, ordersIdOrders, date, datePaid, dateDue);
    }
}
