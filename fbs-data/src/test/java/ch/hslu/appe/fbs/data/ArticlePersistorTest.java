package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;


public class ArticlePersistorTest {


    private ArticlePersistor persistor = new ArticlePersistor();
    private Article article;

    @Before
    public void setup(){
        article = new Article();
        article.setIdArticle(-1);
        article.setName("Main Board");
        article.setAvailable(true);
        article.setArticlenumber(9999);
        article.setDescription("Main boarding mostly");
        article.setInStock(10);
        article.setMinInStock(2);
        article.setPrice(130.00);
        persistor.save(article);

    }

    @Test
    public void save() {
        assertEquals(FBSFeedback.SUCCESS, persistor.save(article));
    }

    @Test
    public void getById() {
        Article testarticle = persistor.getById(-1);
        System.out.println(article.getClass()+" "+testarticle.getClass());
        assertEquals(article, testarticle);
    }

    @Test
    public void getByArticleNr() {
        List<Article> list = persistor.getByArticleNr(9999);
        for (Article testarticle : list){
            System.out.println(testarticle.getIdArticle()+" "+article.getIdArticle());
            assertEquals(this.article, testarticle);
        }
    }

    @Test
    public void updateStockById() {
        persistor.updateStockById(article.getIdArticle(), 200);
    }

    @Test
    public void getListWithParam() {
        List<Article> list = persistor.getList("main");
        for (Article article : list){
            System.out.println(article.getArticlenumber());
        }
    }

    @Test
    public void getList() {
        List<Article> list = persistor.getList();

        assertTrue(list.contains(article));
    }


    @After
    public void cleanUp(){

    }
}