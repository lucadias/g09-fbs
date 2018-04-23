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


    private final EntityManager entitymanager = DBEntityManager.em;


    public Article getById(int id) {
        transactionBegin();

        Article article = entitymanager.find(Article.class, id);


        //code

        transactionClose();

        return article;
    }

    public Article getByArticleNr(int artNr) { return this.getById(artNr);}

    public FBSFeedback save(Article article) {
        try {
            entitymanager.getTransaction().begin();
            entitymanager.merge(article);
            entitymanager.flush();
        } catch (Exception ex){
            return FBSFeedback.UNKNOWN_ERROR;
        }finally {
            entitymanager.getTransaction().commit();
        }
        return FBSFeedback.SUCCESS;
    }

    public FBSFeedback updateStockById(int id, int amount) { return FBSFeedback.SUCCESS; }

    public List<Article> getList() {
        return this.entitymanager.createQuery("Select a From Article a").getResultList();
    }

    public List<Article> search(String regEx) {
        return getList();
    }

    private void transactionBegin(){
        entitymanager.getTransaction().begin();

    }

    private void transactionClose(){
        entitymanager.getTransaction().commit();

        // entitymanager.close();
        //emfactory.close();

    }




    //


}