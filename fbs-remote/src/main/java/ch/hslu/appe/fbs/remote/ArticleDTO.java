package ch.hslu.appe.fbs.remote;

import java.io.Serializable;

/**
 * Article DTO
 *
 * @author Mischa Gruber
 */
public class ArticleDTO implements Serializable{

    private int id;
    private String name = "";
    private int articleNumber = 0;
    private String description = "";
    private int inStock = 0;
    private int minInStock = 0;
    private float price = 0f;
    private boolean available = true;

    public ArticleDTO(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(int articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMinInStock() {
        return minInStock;
    }

    public void setMinInStock(int minInStock) {
        this.minInStock = minInStock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
