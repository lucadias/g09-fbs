/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luca_
 */
@Entity
@Table(name = "Group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Group1.findAll", query = "SELECT g FROM Group1 g")
    , @NamedQuery(name = "Group1.findByIdGroups", query = "SELECT g FROM Group1 g WHERE g.idGroups = :idGroups")
    , @NamedQuery(name = "Group1.findByName", query = "SELECT g FROM Group1 g WHERE g.name = :name")})
public class Group1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGroups")
    private Integer idGroups;
    @Column(name = "Name")
    private String name;

    public Group1() {
    }

    public Group1(Integer idGroups) {
        this.idGroups = idGroups;
    }

    public Integer getIdGroups() {
        return idGroups;
    }

    public void setIdGroups(Integer idGroups) {
        this.idGroups = idGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGroups != null ? idGroups.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Group1)) {
            return false;
        }
        Group1 other = (Group1) object;
        if ((this.idGroups == null && other.idGroups != null) || (this.idGroups != null && !this.idGroups.equals(other.idGroups))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.appe.fbs.model.entities.Group1[ idGroups=" + idGroups + " ]";
    }
    
}
