package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;


public class ArticlePersistorIT {


    private ArticlePersistor persistor = new ArticlePersistor();
    private Article article;

    @Before
    public void setup(){
        article = new Article();
        article.setName("Main Board123");
        article.setAvailable(true);
        article.setArticlenumber(9999);
        article.setDescription("Main boarding mostly");
        article.setInStock(10);
        article.setMinInStock(2);
        article.setPrice(130.00);
        this.article = persistor.save(article);

    }

    @Test
    public void testSave() {
        assertEquals(article, persistor.save(article));
    }

    @Test
    public void testGetById() {
        Article testarticle = persistor.getById(article.getIdArticle());
        System.out.println(article.getClass()+" "+testarticle.getClass());
        assertEquals(article, testarticle);
    }

    @Test
    public void testGetByArticleNr() {
        List<Article> list = persistor.getByArticleNr(article.getArticlenumber());
        assertTrue(list.contains(this.article));
    }

    @Test
    public void testUpdateStockById() {
        persistor.updateStockById(article.getIdArticle(), 200);
    }

    @Test
    public void testGetListWithParam() {
        List<Article> list = persistor.getList("main");
        for (Article article : list){
            System.out.println(article.getArticlenumber());
        }
    }

    @Test
    public void testGetList() {
        List<Article> list = persistor.getList();

        assertTrue(list.contains(article));
    }


    @After
    public void cleanUp(){
        persistor.deleteTestArticles();
    }
}