/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luca_
 */
@Entity
@Table(name = "Order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o")
    , @NamedQuery(name = "Order1.findByIdOrders", query = "SELECT o FROM Order1 o WHERE o.idOrders = :idOrders")
    , @NamedQuery(name = "Order1.findByDate", query = "SELECT o FROM Order1 o WHERE o.date = :date")
    , @NamedQuery(name = "Order1.findByTotalPrice", query = "SELECT o FROM Order1 o WHERE o.totalPrice = :totalPrice")})
public class Order1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrders")
    private Integer idOrders;
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "TotalPrice")
    private Long totalPrice;
    @JoinColumn(name = "Employee_idEmployee", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employee employeeidEmployee;
    @JoinColumn(name = "OrderState_idOrderState", referencedColumnName = "idOrderState")
    @ManyToOne(optional = false)
    private OrderState orderStateidOrderState;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderidOrders")
    private Collection<Bill> billCollection;

    public Order1() {
    }

    public Order1(Integer idOrders) {
        this.idOrders = idOrders;
    }

    public Integer getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders) {
        this.idOrders = idOrders;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Employee getEmployeeidEmployee() {
        return employeeidEmployee;
    }

    public void setEmployeeidEmployee(Employee employeeidEmployee) {
        this.employeeidEmployee = employeeidEmployee;
    }

    public OrderState getOrderStateidOrderState() {
        return orderStateidOrderState;
    }

    public void setOrderStateidOrderState(OrderState orderStateidOrderState) {
        this.orderStateidOrderState = orderStateidOrderState;
    }

    @XmlTransient
    public Collection<Bill> getBillCollection() {
        return billCollection;
    }

    public void setBillCollection(Collection<Bill> billCollection) {
        this.billCollection = billCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrders != null ? idOrders.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.idOrders == null && other.idOrders != null) || (this.idOrders != null && !this.idOrders.equals(other.idOrders))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.appe.fbs.model.entities.Order1[ idOrders=" + idOrders + " ]";
    }
    
}
