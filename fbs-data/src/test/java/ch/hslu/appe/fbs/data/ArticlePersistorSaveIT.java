package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;


public class ArticlePersistorSaveIT {


    private ArticlePersistor persistor = new ArticlePersistor();
    private Article article;

    @Before
    public void setup(){
        article = new Article();
        article.setIdArticle(-1);
        article.setName("Main Board123");
        article.setAvailable(true);
        article.setArticlenumber(9999);
        article.setDescription("Main boarding mostly");
        article.setInStock(10);
        article.setMinInStock(2);
        article.setPrice(130.00);


    }

    @Test
    public void testSave(){
        System.out.println(persistor.save(article).getIdArticle());
        System.out.println(article.getIdArticle());


        article.setName("boay");
        persistor.save(article);



        article.setName("asdf");
        persistor.save(article);


        int test = persistor.getById(article.getIdArticle()).getIdArticle();
        int test1 = this.article.getIdArticle();
        assertEquals(test1, test);

    }

    @After
    public void cleanUp(){
        persistor.deleteTestArticles();
    }
}