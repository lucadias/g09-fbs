package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * DTO of Article Entity.
 *
 * @author Mischa Gruber
 */
public final class ArticleDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private int id;
    private String name = "";
    private int articleNumber = 0;
    private String description = "";
    private int inStock = 0;
    private int minInStock = 0;
    private double price = 0;
    private boolean available = true;

    /**
     * Constructor of the ArticleDTO.
     * The id has to be given, because there is no setter method
     * for the id.
     * @param id the id to set
     */
    public ArticleDTO(final int id) {
        this.id = id;
    }

    /**
     * Returns the database id of the article.
     * @return id of the article
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the article.
     * @return name of the article
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the article.
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the article number of the article.
     * @return article number of the article
     */
    public int getArticleNumber() {
        return articleNumber;
    }

    /**
     * Sets the article number of the article.
     * @param articleNumber the article number to set
     */
    public void setArticleNumber(final int articleNumber) {
        this.articleNumber = articleNumber;
    }

    /**
     * Returns the description of the article.
     * @return description of the article
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the article.
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Returns the amount of articles, that are in stock.
     * @return amount of articles in stock
     */
    public int getInStock() {
        return inStock;
    }

    /**
     * Sets the amount of articles, that are in stock.
     * @param inStock the amount to set
     */
    public void setInStock(final int inStock) {
        this.inStock = inStock;
    }

    /**
     * Returns the minimum number of articles, that has to be in stock.
     * @return minimum of articles in stock as an int
     */
    public int getMinInStock() {
        return minInStock;
    }

    /**
     * Sets the minimum number of articles, that has to be in stock.
     * @param minInStock the minimum to set
     */
    public void setMinInStock(final int minInStock) {
        this.minInStock = minInStock;
    }

    /**
     * Returns the price of the article.
     * @return price of the article in CHF
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the article.
     * @param price the price to set
     */
    public void setPrice(final double price) {
        this.price = price;
    }

    /**
     * Returns if the article is available to sell.
     * @return available to sell as a boolean
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets if the article is available to sell.
     * @param available the boolean to set
     */
    public void setAvailable(final boolean available) {
        this.available = available;
    }
}
