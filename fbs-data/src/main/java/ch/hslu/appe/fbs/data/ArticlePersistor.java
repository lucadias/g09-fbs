package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import javax.persistence.EntityManager;
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

    public List<Article> getList(String regex){


        String query = "";
        try {
            int regexint = Integer.parseInt(regex);
            query = "SELECT c FROM Article c WHERE c.articlenumber LIKE :regexint";
        }catch (NumberFormatException nfe) {
            query = "SELECT c FROM Article c WHERE c.name LIKE :regex OR c.description LIKE :regex";
        } finally {
            return this.entitymanager.createQuery(query)
                    .setParameter("regex", regex)
                    .getResultList();
        }
}

    public Article getByArticleNr(int artNr) { return this.getById(artNr);}

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
    public FBSFeedback updateStockById(int id, int amount) { return FBSFeedback.SUCCESS; }


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


}