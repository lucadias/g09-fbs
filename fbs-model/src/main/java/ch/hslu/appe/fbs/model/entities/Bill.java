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
@Table(name = "Bill")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bill.findAll", query = "SELECT b FROM Bill b")
    , @NamedQuery(name = "Bill.findByIdBill", query = "SELECT b FROM Bill b WHERE b.idBill = :idBill")
    , @NamedQuery(name = "Bill.findByTotal", query = "SELECT b FROM Bill b WHERE b.total = :total")
    , @NamedQuery(name = "Bill.findByDate", query = "SELECT b FROM Bill b WHERE b.date = :date")
    , @NamedQuery(name = "Bill.findByDatePaid", query = "SELECT b FROM Bill b WHERE b.datePaid = :datePaid")
    , @NamedQuery(name = "Bill.findByDateDue", query = "SELECT b FROM Bill b WHERE b.dateDue = :dateDue")})
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBill")
    private Integer idBill;
    @Column(name = "Total")
    private Long total;
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "DatePaid")
    @Temporal(TemporalType.DATE)
    private Date datePaid;
    @Column(name = "DateDue")
    @Temporal(TemporalType.DATE)
    private Date dateDue;
    @JoinColumn(name = "Client_idClients", referencedColumnName = "idClients")
    @ManyToOne(optional = false)
    private Client clientidClients;
    @JoinColumn(name = "Orders_idOrders", referencedColumnName = "idOrders")
    @ManyToOne(optional = false)
    private Orders ordersidOrders;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billidBill")
    private Collection<Overdue> overdueCollection;

    public Bill() {
    }

    public Bill(Integer idBill) {
        this.idBill = idBill;
    }

    public Integer getIdBill() {
        return idBill;
    }

    public void setIdBill(Integer idBill) {
        this.idBill = idBill;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Client getClientidClients() {
        return clientidClients;
    }

    public void setClientidClients(Client clientidClients) {
        this.clientidClients = clientidClients;
    }

    public Orders getOrdersidOrders() {
        return ordersidOrders;
    }

    public void setOrdersidOrders(Orders ordersidOrders) {
        this.ordersidOrders = ordersidOrders;
    }

    @XmlTransient
    public Collection<Overdue> getOverdueCollection() {
        return overdueCollection;
    }

    public void setOverdueCollection(Collection<Overdue> overdueCollection) {
        this.overdueCollection = overdueCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBill != null ? idBill.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bill)) {
            return false;
        }
        Bill other = (Bill) object;
        if ((this.idBill == null && other.idBill != null) || (this.idBill != null && !this.idBill.equals(other.idBill))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.appe.fbs.model.entities.Bill[ idBill=" + idBill + " ]";
    }
    
}
