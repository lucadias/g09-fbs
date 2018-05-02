package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import javax.persistence.EntityManager;
import java.util.List;


//Hotfix


/**
 * JavaDoc
 *
 * @author Pascal Stalder
 */
public class ArticlePersistor {


    private final EntityManager entitymanager = Util.entityManager;


    public Article getById(int id) {
        transactionBegin();

        Article article = entitymanager.find(Article.class, id);

        transactionCommit();

        return article;
    }

    public Article getByArticleNr(int artNr) { return this.getById(artNr);}

    public FBSFeedback save(Article article) {
        return Util.save(article);
    }

    public FBSFeedback updateStockById(int id, int amount) { return FBSFeedback.SUCCESS; }

    public List<Article> getList() {
        this.transactionBegin();
        List<Article> list = this.entitymanager.createQuery("Select a From Article a").getResultList();
        this.transactionCommit();
        return list;
    }

    public List<Article> search(String regEx) {
        return getList();
    }

    private void transactionBegin(){
        entitymanager.getTransaction().begin();
    }

    private void transactionCommit(){
        entitymanager.getTransaction().commit();
    }




    //


}