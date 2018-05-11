package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArticlePersistorTest {


    public static ArticlePersistor persistor = new ArticlePersistor();
    public static Article article;

    @BeforeClass
    public static void setup(){
        article = new Article();
        article.setName("Main Board");
        article.setAvailable(true);
        article.setArticlenumber(500);
        article.setDescription("Main boarding mostly...");
        article.setInStock(10);
        article.setMinInStock(2);
        article.setPrice(130.00);

    }

    @Test
    public void getById() {
    }

    @Test
    public void getByArticleNr() {
    }

    @Test
    public void save() {
    }

    @Test
    public void updateStockById() {
    }

    @Test
    public void getList() {
    }

    @Test
    public void search() {
    }
}