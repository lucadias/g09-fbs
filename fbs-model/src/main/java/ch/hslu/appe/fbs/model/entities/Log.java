package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Log")
public class Log {
    private int idLog;
    private String table;
    private String change;
    private Timestamp datetime;

    @Id
    @Column(name = "idLog", nullable = false)
    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    @Basic
    @Column(name = "Table", nullable = true, length = 45)
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Basic
    @Column(name = "Change", nullable = true, length = 45)
    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Basic
    @Column(name = "Datetime", nullable = true)
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return idLog == log.idLog &&
                Objects.equals(table, log.table) &&
                Objects.equals(change, log.change) &&
                Objects.equals(datetime, log.datetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idLog, table, change, datetime);
    }
}
