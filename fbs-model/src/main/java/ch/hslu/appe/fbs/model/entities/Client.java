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
@Table(name = "Client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
    , @NamedQuery(name = "Client.findByIdClients", query = "SELECT c FROM Client c WHERE c.idClients = :idClients")
    , @NamedQuery(name = "Client.findBySurname", query = "SELECT c FROM Client c WHERE c.surname = :surname")
    , @NamedQuery(name = "Client.findByFirstname", query = "SELECT c FROM Client c WHERE c.firstname = :firstname")
    , @NamedQuery(name = "Client.findByAdress", query = "SELECT c FROM Client c WHERE c.adress = :adress")
    , @NamedQuery(name = "Client.findByActive", query = "SELECT c FROM Client c WHERE c.active = :active")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClients")
    private Integer idClients;
    @Column(name = "Surname")
    private String surname;
    @Column(name = "Firstname")
    private String firstname;
    @Column(name = "Adress")
    private String adress;
    @Column(name = "Active")
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientidClients")
    private Collection<Bill> billCollection;

    public Client() {
    }

    public Client(Integer idClients) {
        this.idClients = idClients;
    }

    public Integer getIdClients() {
        return idClients;
    }

    public void setIdClients(Integer idClients) {
        this.idClients = idClients;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        hash += (idClients != null ? idClients.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.idClients == null && other.idClients != null) || (this.idClients != null && !this.idClients.equals(other.idClients))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.appe.fbs.model.entities.Client[ idClients=" + idClients + " ]";
    }
    
}
