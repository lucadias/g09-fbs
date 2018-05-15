package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Reorder")
public class Reorder {
    private int idReorder;
    private Timestamp date;
    private int articleIdArticle;
    private int orderStateIdOrderState;
    private Integer amount;

    @Id
    @Column(name = "idReorder", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getIdReorder() {
        return idReorder;
    }

    public void setIdReorder(int idReorder) {
        this.idReorder = idReorder;
    }

    @Basic
    @Column(name = "Date", nullable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "Article_idArticle", nullable = false)
    public int getArticleIdArticle() {
        return articleIdArticle;
    }

    public void setArticleIdArticle(int articleIdArticle) {
        this.articleIdArticle = articleIdArticle;
    }

    @Basic
    @Column(name = "OrderState_idOrderState", nullable = false)
    public int getOrderStateIdOrderState() {
        return orderStateIdOrderState;
    }

    public void setOrderStateIdOrderState(int orderStateIdOrderState) {
        this.orderStateIdOrderState = orderStateIdOrderState;
    }

    @Basic
    @Column(name = "Amount", nullable = true)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reorder reorder = (Reorder) o;
        return idReorder == reorder.idReorder &&
                articleIdArticle == reorder.articleIdArticle &&
                orderStateIdOrderState == reorder.orderStateIdOrderState &&
                Objects.equals(date, reorder.date) &&
                Objects.equals(amount, reorder.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idReorder, date, articleIdArticle, orderStateIdOrderState, amount);
    }
}
