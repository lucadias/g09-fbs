package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.OrderedArticles;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class OrderedArticlePersistorIT {
    public static OrderedArticles article;
    public static OrderedArticlePersistor persistor = new OrderedArticlePersistor();

    @BeforeClass
    public static void setup(){
        article = new OrderedArticles();
        article.setAmount(3);
        article.setArticleIdArticle(1);
        article.setIdOrderedArticles(3);
        article.setOrdersIdOrder(2);
        article.setTotalPrice(150.00);
    }

    @Test
    public void save() {
    }
}