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
@Table(name = "Log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l")
    , @NamedQuery(name = "Log.findByIdLog", query = "SELECT l FROM Log l WHERE l.idLog = :idLog")
    , @NamedQuery(name = "Log.findByTable", query = "SELECT l FROM Log l WHERE l.table = :table")
    , @NamedQuery(name = "Log.findByChange", query = "SELECT l FROM Log l WHERE l.change = :change")
    , @NamedQuery(name = "Log.findByDatetime", query = "SELECT l FROM Log l WHERE l.datetime = :datetime")})
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLog")
    private Integer idLog;
    @Column(name = "Table")
    private String table;
    @Column(name = "Change")
    private String change;
    @Column(name = "Datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    public Log() {
    }

    public Log(Integer idLog) {
        this.idLog = idLog;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.appe.fbs.model.entities.Log[ idLog=" + idLog + " ]";
    }
    
}
