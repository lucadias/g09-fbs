package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * ArticleConverter
 *
 * @author Mischa Gruber
 */
public final class ArticleConverter {

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

    public List<ArticleDTO> convertToDTO(final List<Article> articleList) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            articleDTOList.add(convertToDTO(article));
        }

        return articleDTOList;
    }

    public Article convertToEntity(final ArticleDTO articleDTO) {
        Article article = new Article();
        article.setIdArticle(articleDTO.getId());
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
