package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * OrderedArticleDTO
 *
 * @author Mischa Gruber
 */
public final class OrderedArticleDTO implements Serializable {

    private int id;
    private ArticleDTO articleDTO;
    private int amount;
    private double totalPrice;

    public OrderedArticleDTO(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArticleDTO getArticleDTO() {
        return articleDTO;
    }

    public void setArticleDTO(final ArticleDTO articleDTO) {
        this.articleDTO = articleDTO;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
