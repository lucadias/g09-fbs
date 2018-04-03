/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luca_
 */
@Entity
@Table(name = "OrderState")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderState.findAll", query = "SELECT o FROM OrderState o")
    , @NamedQuery(name = "OrderState.findByIdOrderState", query = "SELECT o FROM OrderState o WHERE o.idOrderState = :idOrderState")
    , @NamedQuery(name = "OrderState.findByState", query = "SELECT o FROM OrderState o WHERE o.state = :state")})
public class OrderState implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrderState")
    private Integer idOrderState;
    @Column(name = "State")
    private String state;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderStateidOrderState")
    private Collection<Orders> ordersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderStateidOrderState")
    private Collection<Reorder> reorderCollection;

    public OrderState() {
    }

    public OrderState(Integer idOrderState) {
        this.idOrderState = idOrderState;
    }

    public Integer getIdOrderState() {
        return idOrderState;
    }

    public void setIdOrderState(Integer idOrderState) {
        this.idOrderState = idOrderState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    @XmlTransient
    public Collection<Reorder> getReorderCollection() {
        return reorderCollection;
    }

    public void setReorderCollection(Collection<Reorder> reorderCollection) {
        this.reorderCollection = reorderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrderState != null ? idOrderState.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderState)) {
            return false;
        }
        OrderState other = (OrderState) object;
        if ((this.idOrderState == null && other.idOrderState != null) || (this.idOrderState != null && !this.idOrderState.equals(other.idOrderState))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.appe.fbs.model.entities.OrderState[ idOrderState=" + idOrderState + " ]";
    }
    
}
