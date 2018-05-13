package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


//Hotfix


/**
 * Persitor for saving and retrieving Article entities.
 *
 * @author Pascal Stalder
 */
public class ArticlePersistor {


    private final EntityManager entitymanager = Util.entityManager;


    /**
     * Returns Article entity with id.
     * @param id
     * @return Article
     */
    public Article getById(int id) {
        Util.transactionBegin();

        Article article = entitymanager.find(Article.class, id);

        Util.transactionCommit();

        return article;
    }

    public List<Article> getList(String searchtext){

        String regex = "%"+searchtext+"%";
        String query = "SELECT a FROM Article a WHERE a.name LIKE :regex OR a.description LIKE :regex OR a.articlenumber LIKE :regex";
            return this.entitymanager.createQuery(query)
                    .setParameter("regex", regex)
                    .getResultList();
        }

    public Article getByArticleNr(int artNr) {
        try {
            return (Article) this.entitymanager.createQuery("SELECT a FROM Article a WHERE a.articlenumber = :artNr")
                    .setParameter("artNr", artNr)
                    .getSingleResult();

        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * Saves Article object to database.
     * @param article
     * @return FBSFeeback
     */
    public FBSFeedback save(Article article) {
        return Util.save(article);
    }

    /**
     * Updates the inStock value of an Article.
     * @param id
     * @param amount
     * @return FSBFeedback
     */
    public FBSFeedback updateStockById(int id, int amount) {
        try {
            Article article = this.getById(id);
            Util.transactionBegin();
            article.setInStock(amount);
            Util.transactionCommit();
            return FBSFeedback.SUCCESS;
        }catch (Exception e){
            System.out.println(e.toString());
            return FBSFeedback.UNKNOWN_ERROR;
        }
        }


    /**
     *
     * @return
     */
    public List<Article> getList() {
        Util.transactionBegin();
        List<Article> list = this.entitymanager.createQuery("Select a From Article a").getResultList();
        Util.transactionCommit();
        return list;
    }

    /**
     * Returns a List of Article's matching regEx.
     * @param regEx
     * @return List&gtArticle&lt
     */
    public List<Article> search(String regEx) {
        List<Article> result = new ArrayList<>();
        for (Article article:this.getList()){
            if(article.getName().matches(regEx)) {
                result.add(article);
            }
        }
        return result;
    }


}