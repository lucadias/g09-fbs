package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Article")
public class Article {
    private int idArticle;
    private String name;
    private Integer articlenumber;
    private String description;
    private Integer inStock;
    private Integer price;
    private Integer minInStock;
    private Boolean available;

    @Id
    @Column(name = "idArticle", nullable = false)
    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    @Basic
    @Column(name = "Name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Articlenumber", nullable = true)
    public Integer getArticlenumber() {
        return articlenumber;
    }

    public void setArticlenumber(Integer articlenumber) {
        this.articlenumber = articlenumber;
    }

    @Basic
    @Column(name = "Description", nullable = true, length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "InStock", nullable = true)
    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    @Basic
    @Column(name = "Price", nullable = true, precision = 0)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "MinInStock", nullable = true)
    public Integer getMinInStock() {
        return minInStock;
    }

    public void setMinInStock(Integer minInStock) {
        this.minInStock = minInStock;
    }

    @Basic
    @Column(name = "Available", nullable = true)
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return idArticle == article.idArticle &&
                Objects.equals(name, article.name) &&
                Objects.equals(articlenumber, article.articlenumber) &&
                Objects.equals(description, article.description) &&
                Objects.equals(inStock, article.inStock) &&
                Objects.equals(price, article.price) &&
                Objects.equals(minInStock, article.minInStock) &&
                Objects.equals(available, article.available);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idArticle, name, articlenumber, description, inStock, price, minInStock, available);
    }
}
