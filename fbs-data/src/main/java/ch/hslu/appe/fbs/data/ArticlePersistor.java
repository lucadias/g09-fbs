package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Persitor for saving and retrieving Article entities.
 *
 * @author Pascal Stalder
 */
public class ArticlePersistor {


    private final EntityManager entitymanager = Util.entityManager;


    /**
     * Returns Article entity with id.
     *
     * @param id id of the wanted article.
     * @return Article
     */
    public Article getById(int id) {
        Util.transactionBegin();

        Article article = entitymanager.find(Article.class, id);

        Util.transactionCommit();

        return article;
    }

    /**
     * Returns a List of Articles that resemble the searchtext
     *
     * @param searchtext String by which is searched for.
     * @return List&gt;Article&lt;
     */
    public List<Article> getList(String searchtext) {

        String regex = "%" + searchtext + "%";
        String query = "SELECT a FROM Article a WHERE a.name LIKE :regex OR a.description LIKE :regex OR a.articlenumber LIKE :regex";
        return this.entitymanager.createQuery(query)
                .setParameter("regex", regex)
                .getResultList();
    }

    public List<Article> search(String searchText){
        return this.getList(searchText);
    }

    /**
     * Returns an Article by an article number
     * @param artNr article number of wanted Article
     * @return Article
     */
    public Article getByArticleNr(int artNr) {
        try {
            return (Article) this.entitymanager.createQuery("SELECT a FROM Article a WHERE a.articlenumber = :artNr")
                    .setParameter("artNr", artNr)
                    .getSingleResult();

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * Saves Article object to database.
     *
     * @param article Article entity to save.
     * @return FBSFeeback
     */
    public FBSFeedback save(Article article) {
        return Util.save(article);
    }


    /**
     * Returns all Articles from database.
     * @return List
     */
    public List<Article> getList() {
        Util.transactionBegin();
        List<Article> list = this.entitymanager.createQuery("Select a From Article a").getResultList();
        Util.transactionCommit();
        return list;
    }

    /**
     * Updates the inStock value of an Article.
     *
     * @param id id of Article.
     * @param amount new amount in stock.
     * @return FSBFeedback
     */
    public FBSFeedback updateStockById(int id, int amount) {
        try {
            Article article = this.getById(id);
            Util.transactionBegin();
            article.setInStock(amount);
            Util.transactionCommit();
            return FBSFeedback.SUCCESS;
        } catch (Exception e) {
            System.out.println(e.toString());
            return FBSFeedback.UNKNOWN_ERROR;
        }
    }

}