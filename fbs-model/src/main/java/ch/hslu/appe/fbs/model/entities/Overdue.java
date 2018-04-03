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
@Table(name = "Overdue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Overdue.findAll", query = "SELECT o FROM Overdue o")
    , @NamedQuery(name = "Overdue.findByIdOverdue", query = "SELECT o FROM Overdue o WHERE o.idOverdue = :idOverdue")
    , @NamedQuery(name = "Overdue.findByDate", query = "SELECT o FROM Overdue o WHERE o.date = :date")
    , @NamedQuery(name = "Overdue.findByDateDue", query = "SELECT o FROM Overdue o WHERE o.dateDue = :dateDue")})
public class Overdue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOverdue")
    private Integer idOverdue;
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "DateDue")
    @Temporal(TemporalType.DATE)
    private Date dateDue;
    @JoinColumn(name = "Bill_idBill", referencedColumnName = "idBill")
    @ManyToOne(optional = false)
    private Bill billidBill;

    public Overdue() {
    }

    public Overdue(Integer idOverdue) {
        this.idOverdue = idOverdue;
    }

    public Integer getIdOverdue() {
        return idOverdue;
    }

    public void setIdOverdue(Integer idOverdue) {
        this.idOverdue = idOverdue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Bill getBillidBill() {
        return billidBill;
    }

    public void setBillidBill(Bill billidBill) {
        this.billidBill = billidBill;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOverdue != null ? idOverdue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Overdue)) {
            return false;
        }
        Overdue other = (Overdue) object;
        if ((this.idOverdue == null && other.idOverdue != null) || (this.idOverdue != null && !this.idOverdue.equals(other.idOverdue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.appe.fbs.model.entities.Overdue[ idOverdue=" + idOverdue + " ]";
    }
    
}
