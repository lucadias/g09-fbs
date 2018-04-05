package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;


//Hotfix


/**
 * JavaDoc
 *
 * @author Pascal Stalder
 */
public class ArticlePersistor {

    private final EntityManagerFactory emfactory;
    private final EntityManager entitymanager;

    public ArticlePersistor() {
        this.emfactory = Persistence.createEntityManagerFactory("luca");

        this.entitymanager = emfactory.createEntityManager();
        }

    public Article getById(int id) {
        transactionBegin();

        Article article = entitymanager.find(Article.class, id);

        //code

        transactionClose();

        return article;
    }

   // public Article getByArticleNr(int artNr) { return new Article(0);}

    public FBSFeedback save(Article article) { return FBSFeedback.SUCCESS; }

    public FBSFeedback updateStockById(int id, int amount) { return FBSFeedback.SUCCESS; }

    public List<Article> getList() {
        List<Article> list = new ArrayList<>();
        //list.add(new Article(0));
        //list.add(new Article(1));
        return list;
    }

    public List<Article> search(String regEx) {
        return getList();
    }

    private void transactionBegin(){
        entitymanager.getTransaction().begin();

    }

    private void transactionClose(){
        entitymanager.close();
        emfactory.close();

    }




    //entitymanager.persist( article );
    //entitymanager.getTransaction( ).commit( );


}