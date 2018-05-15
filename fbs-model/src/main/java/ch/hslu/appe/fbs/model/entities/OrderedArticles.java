package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OrderedArticles")
public class OrderedArticles {
    private int idOrderedArticles;
    private int ordersIdOrder;
    private int articleIdArticle;
    private Integer amount;
    private Double totalPrice;

    @Id
    @Column(name = "idOrderedArticles", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getIdOrderedArticles() {
        return idOrderedArticles;
    }

    public void setIdOrderedArticles(int idOrderedArticles) {
        this.idOrderedArticles = idOrderedArticles;
    }

    @Basic
    @Column(name = "Orders_idOrder", nullable = false)
    public int getOrdersIdOrder() {
        return ordersIdOrder;
    }

    public void setOrdersIdOrder(int ordersIdOrder) {
        this.ordersIdOrder = ordersIdOrder;
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
    @Column(name = "Amount", nullable = true)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "TotalPrice", nullable = true, precision = 0)
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedArticles that = (OrderedArticles) o;
        return idOrderedArticles == that.idOrderedArticles &&
                ordersIdOrder == that.ordersIdOrder &&
                articleIdArticle == that.articleIdArticle &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idOrderedArticles, ordersIdOrder, articleIdArticle, amount, totalPrice);
    }
}
