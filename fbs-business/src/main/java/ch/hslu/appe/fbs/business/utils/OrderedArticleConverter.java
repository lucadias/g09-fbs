package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.OrderedArticles;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;

/**
 * Class_Name
 *
 * @author Mischa Gruber
 */
public final class OrderedArticleConverter {

    private ArticleConverter articleConverter;

    public OrderedArticleConverter() {
        this.articleConverter = new ArticleConverter();
    }

    public OrderedArticleDTO convertToDTO(final OrderedArticles orderedArticles, final ArticleDTO articleDTO) {
        OrderedArticleDTO orderedArticleDTO = new OrderedArticleDTO(orderedArticles.getIdOrderedArticles());
        orderedArticleDTO.setArticleDTO(articleDTO);
        orderedArticleDTO.setAmount(orderedArticles.getAmount());
        orderedArticleDTO.setTotalPrice(orderedArticles.getTotalPrice());

        return orderedArticleDTO;
    }

    public OrderedArticles convertToEntity(final OrderedArticleDTO orderedArticleDTO, final int orderId) {
        OrderedArticles orderedArticles = new OrderedArticles();
        orderedArticles.setIdOrderedArticles(orderedArticleDTO.getId());
        orderedArticles.setArticleIdArticle(orderedArticleDTO.getArticleDTO().getId());
        orderedArticles.setOrdersIdOrder(orderId);
        orderedArticles.setAmount(orderedArticleDTO.getAmount());
        orderedArticles.setTotalPrice(orderedArticleDTO.getTotalPrice());

        return orderedArticles;
    }
}
