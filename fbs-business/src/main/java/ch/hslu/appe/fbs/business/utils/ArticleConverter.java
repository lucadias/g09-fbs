package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter for article entity and DTO.
 *
 * @author Mischa Gruber
 */
public final class ArticleConverter {

    /**
     * Converts an article entity into a DTO.
     * @param article article to be converted
     * @return converted article
     */
    public ArticleDTO convertToDTO(final Article article) {
        ArticleDTO articleDTO = new ArticleDTO(article.getIdArticle());
        articleDTO.setName(article.getName());
        articleDTO.setArticleNumber(article.getArticlenumber());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setInStock(article.getInStock());
        articleDTO.setMinInStock(article.getMinInStock());
        articleDTO.setPrice(article.getPrice());
        articleDTO.setAvailable(article.getAvailable());

        return articleDTO;
    }

    /**
     * Converts a list of article entities into DTOs.
     * @param articleList list to be converted
     * @return converted list
     */
    public List<ArticleDTO> convertToDTO(final List<Article> articleList) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            articleDTOList.add(convertToDTO(article));
        }

        return articleDTOList;
    }

    /**
     * Converts an article DTO into an entitiy.
     * @param articleDTO article to be converted
     * @return converted article
     */
    public Article convertToEntity(final ArticleDTO articleDTO) {
        Article article = new Article();
        if (articleDTO.getId() != -1) {
            article.setIdArticle(articleDTO.getId());
        }
        article.setName(articleDTO.getName());
        article.setArticlenumber(articleDTO.getArticleNumber());
        article.setDescription(articleDTO.getDescription());
        article.setInStock(articleDTO.getInStock());
        article.setMinInStock(articleDTO.getMinInStock());
        article.setPrice(articleDTO.getPrice());
        article.setAvailable(articleDTO.isAvailable());

        return article;
    }
}
