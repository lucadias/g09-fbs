package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Overdue")
public class Overdue {
    private int idOverdue;
    private int billIdBill;
    private Date date;
    private Date dateDue;

    @Id
    @Column(name = "idOverdue", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getIdOverdue() {
        return idOverdue;
    }

    public void setIdOverdue(int idOverdue) {
        this.idOverdue = idOverdue;
    }

    @Basic
    @Column(name = "Bill_idBill", nullable = false)
    public int getBillIdBill() {
        return billIdBill;
    }

    public void setBillIdBill(int billIdBill) {
        this.billIdBill = billIdBill;
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
        Overdue overdue = (Overdue) o;
        return idOverdue == overdue.idOverdue &&
                billIdBill == overdue.billIdBill &&
                Objects.equals(date, overdue.date) &&
                Objects.equals(dateDue, overdue.dateDue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idOverdue, billIdBill, date, dateDue);
    }
}
