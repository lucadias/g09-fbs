package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

@Ignore
public class ArticlePersistorTest {


    public static ArticlePersistor persistor = new ArticlePersistor();
    public static Article article;

    @BeforeClass
    public static void setup(){
        article = new Article();
        article.setIdArticle(9780);
        article.setName("Main Board");
        article.setAvailable(true);
        article.setArticlenumber(400);
        article.setDescription("Main boarding mostly");
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
        assertEquals(FBSFeedback.SUCCESS, persistor.save(article));
    }

    @Test
    public void updateStockById() {
        persistor.updateStockById(article.getIdArticle(), 200);
    }

    @Test
    public void getList() {
        List<Article> list = persistor.getList("main");
        for (Article article : list){
            System.out.println(article.getArticlenumber());
        }
    }

    @Test
    public void search() {
    }
}