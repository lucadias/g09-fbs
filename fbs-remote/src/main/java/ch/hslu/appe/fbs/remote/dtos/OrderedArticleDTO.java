package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * DTO of OrderedArticle Entity.
 *
 * @author Mischa Gruber
 */
public final class OrderedArticleDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private int id;
    private ArticleDTO articleDTO;
    private int amount;
    private double totalPrice;

    /**
     * Constructor of the OrderedArticleDTO.
     * The id has to be given, because there is no setter method
     * for the id.
     * @param id the id to set
     */
    public OrderedArticleDTO(final int id) {
        this.id = id;
    }

    /**
     * Returns the database id of the ordered article.
     * @return id of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the ArticleDTO object of the ordered article.
     * @return ArticleDTO of the ordered article
     */
    public ArticleDTO getArticleDTO() {
        return articleDTO;
    }

    /**
     * Sets the ArticleDTO object of the ordered article.
     * @param articleDTO ArticleDTO to set
     */
    public void setArticleDTO(final ArticleDTO articleDTO) {
        this.articleDTO = articleDTO;
    }

    /**
     * Returns the amount of the articles.
     * @return amout of the articles
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the articles.
     * @param amount amount to set
     */
    public void setAmount(final int amount) {
        this.amount = amount;
    }

    /**
     * Returns the total price of the articles.
     * @return total price of the articles
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the articles.
     * @param totalPrice total price to set
     */
    public void setTotalPrice(final double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        OrderedArticleDTO orderedArticleDTO = (OrderedArticleDTO) obj;

        return orderedArticleDTO.getId() == this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}
