package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.data.ArticlePersistor;
import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class ArticleConverterIT {

    private static ArticlePersistor articlePersistor;
    private static ArticleConverter articleConverter;
    private static Article article;

    @BeforeClass
    public static void init(){
        articlePersistor = new ArticlePersistor();
        articleConverter = new ArticleConverter();

        article = new Article();
        article.setName("ConverterTest");
        article.setAvailable(true);
        article.setArticlenumber(9999);
        article.setDescription("Converter");
        article.setInStock(10);
        article.setMinInStock(2);
        article.setPrice(130.00);
        article = articlePersistor.save(article);
    }

    @Test
    public void testConvertToDTO() {
        ArticleDTO articleDTO = articleConverter.convertToDTO(article);
        assertEquals(article.getIdArticle(), new Integer(articleDTO.getId()));
        assertEquals(article.getName(), articleDTO.getName());
    }
}
