package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.OrderedArticles;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter for OrderArticle entity and DTO.
 *
 * @author Mischa Gruber
 */
public final class OrderedArticleConverter {

    /**
     * Converts an OrderedArticle entity into an DTO with an additional given DTO.
     * The additional given DTO will be set and have to be converted before this operation.
     * @param orderedArticles OrderedArticle to be converted
     * @param articleDTO already converted article
     * @return converted OrderedArticle
     */
    public OrderedArticleDTO convertToDTO(final OrderedArticles orderedArticles, final ArticleDTO articleDTO) {
        OrderedArticleDTO orderedArticleDTO = new OrderedArticleDTO(orderedArticles.getIdOrderedArticles());
        orderedArticleDTO.setArticleDTO(articleDTO);
        orderedArticleDTO.setAmount(orderedArticles.getAmount());
        orderedArticleDTO.setTotalPrice(orderedArticles.getTotalPrice());

        return orderedArticleDTO;
    }

    /**
     * Converts an OrderedArticle DTO into an entity.
     * The additional DTO have to be converted separately before or after this operation to save
     * all data informations.
     * @param orderedArticleDTO OrderedArticle to be converted
     * @return converted OrderedArticle
     */
    public OrderedArticles convertToEntity(final OrderedArticleDTO orderedArticleDTO, final int orderId) {
        OrderedArticles orderedArticles = new OrderedArticles();
        if (orderedArticleDTO.getId() != -1)
            orderedArticles.setIdOrderedArticles(orderedArticleDTO.getId());
        orderedArticles.setArticleIdArticle(orderedArticleDTO.getArticleDTO().getId());
        orderedArticles.setOrdersIdOrder(orderId);
        orderedArticles.setAmount(orderedArticleDTO.getAmount());
        orderedArticles.setTotalPrice(orderedArticleDTO.getTotalPrice());

        return orderedArticles;
    }

    public List<OrderedArticles> convertToEntityList(final List<OrderedArticleDTO> orderedArticlesDTOList, final int orderId) {
        List<OrderedArticles> orderedArticlesEntityList = new ArrayList<>();
        for (OrderedArticleDTO orderedArticlesDTO : orderedArticlesDTOList) {
            orderedArticlesEntityList.add(convertToEntity(orderedArticlesDTO, orderId));
        }

        return orderedArticlesEntityList;
    }
}
