package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * Article DTO
 *
 * @author Mischa Gruber
 */
public final class ArticleDTO implements Serializable {

    private int id;
    private String name = "";
    private int articleNumber = 0;
    private String description = "";
    private int inStock = 0;
    private int minInStock = 0;
    private double price = 0;
    private boolean available = true;

    public ArticleDTO(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(final int articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(final int inStock) {
        this.inStock = inStock;
    }

    public int getMinInStock() {
        return minInStock;
    }

    public void setMinInStock(final int minInStock) {
        this.minInStock = minInStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(final boolean available) {
        this.available = available;
    }
}
