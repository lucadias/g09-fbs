/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luca_
 */
@Entity
@Table(name = "Reorder")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reorder.findAll", query = "SELECT r FROM Reorder r")
    , @NamedQuery(name = "Reorder.findByIdReorder", query = "SELECT r FROM Reorder r WHERE r.idReorder = :idReorder")
    , @NamedQuery(name = "Reorder.findByDate", query = "SELECT r FROM Reorder r WHERE r.date = :date")
    , @NamedQuery(name = "Reorder.findByAmount", query = "SELECT r FROM Reorder r WHERE r.amount = :amount")})
public class Reorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idReorder")
    private Integer idReorder;
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "Amount")
    private Integer amount;
    @JoinColumn(name = "Article_idArticle", referencedColumnName = "idArticle")
    @ManyToOne(optional = false)
    private Article articleidArticle;
    @JoinColumn(name = "OrderState_idOrderState", referencedColumnName = "idOrderState")
    @ManyToOne(optional = false)
    private OrderState orderStateidOrderState;

    public Reorder() {
    }

    public Reorder(Integer idReorder) {
        this.idReorder = idReorder;
    }

    public Integer getIdReorder() {
        return idReorder;
    }

    public void setIdReorder(Integer idReorder) {
        this.idReorder = idReorder;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Article getArticleidArticle() {
        return articleidArticle;
    }

    public void setArticleidArticle(Article articleidArticle) {
        this.articleidArticle = articleidArticle;
    }

    public OrderState getOrderStateidOrderState() {
        return orderStateidOrderState;
    }

    public void setOrderStateidOrderState(OrderState orderStateidOrderState) {
        this.orderStateidOrderState = orderStateidOrderState;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReorder != null ? idReorder.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reorder)) {
            return false;
        }
        Reorder other = (Reorder) object;
        if ((this.idReorder == null && other.idReorder != null) || (this.idReorder != null && !this.idReorder.equals(other.idReorder))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.appe.fbs.model.entities.Reorder[ idReorder=" + idReorder + " ]";
    }
    
}
