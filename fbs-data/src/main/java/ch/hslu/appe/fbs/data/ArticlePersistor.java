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
public final class ArticlePersistor {


    private final EntityManager entitymanager = Util.entityManager;


    /**
     * Returns Article entity with id.
     *
     * @param id id of the wanted article.
     * @return Article
     */
    public Article getById(final int id) {
        Util.transactionBegin();

        Article article = entitymanager.find(Article.class, id);

        Util.transactionCommit();


        return article;
    }

    /**
     * Returns a List of Articles that resemble the searchtext.
     *
     * @param searchtext String by which is searched for.
     * @return List&gt;Article&lt;
     */
    public List<Article> getList(final String searchtext) {

        String regex = "%" + searchtext + "%";
        //noinspection JpaQlInspection
        String query = "SELECT a FROM Article a WHERE a.name LIKE :regex OR a.description LIKE :regex OR CONCAT(a.articlenumber, '') LIKE :regex";
        return this.entitymanager.createQuery(query)
                .setParameter("regex", regex)
                .getResultList();
    }

    public List<Article> search(final String searchText){
        return this.getList(searchText);
    }

    /**
     * Returns Articles by an article number.
     * @param artNr article number of wanted Article.
     * @return List&gt;Article&lt;
     */
    public List<Article> getByArticleNr(final int artNr) {
        try {
            return this.entitymanager.createQuery("SELECT a FROM Article a WHERE a.articlenumber = :artNr")
                    .setParameter("artNr", artNr)
                    .getResultList();

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * Saves or updates Article object to database.
     * if Article has no idArticle it is autogenerated.
     *
     * @param article Article entity to save.
     * @return FBSFeedback
     */
    public Article save(Article article) {

        try {
            Util.transactionBegin();
            if(article.getIdArticle() == null){
                Util.entityManager.persist(article);
            } else {
                Util.entityManager.merge(article);
            }
            Util.entityManager.flush();
            Util.transactionCommit();
        } catch (Exception e){
            System.out.println(e.toString());
            Util.transactionCommit();
        }
        return article;
    }

    public FBSFeedback delete(Article article) {

        try {
            Util.transactionBegin();
            Util.entityManager.remove(article);
            Util.entityManager.flush();
            Util.transactionCommit();
            return FBSFeedback.SUCCESS;
        } catch (Exception e){
            System.out.println(e.toString());

            Util.transactionCommit();
        }
        return  FBSFeedback.UNKNOWN_ERROR;

    }

    public FBSFeedback deleteTestArticles() {

        for(Article ta : this.getByArticleNr(9999)){
            try {
                Util.transactionBegin();
                Util.entityManager.remove(ta);
                Util.entityManager.flush();
                Util.transactionCommit();

            } catch (Exception e){
                System.out.println(e.toString());
                Util.transactionCommit();
                return  FBSFeedback.UNKNOWN_ERROR;

            }


        }
        return  FBSFeedback.SUCCESS;
    }

    /**
     * Returns all Articles from database.
     * @return List&gt;Article&lt;
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
    public FBSFeedback updateStockById(final int id, final int amount) {
        try {
            Article article = this.getById(id);
            Util.transactionBegin();
            article.setInStock(amount);
            Util.transactionCommit();
            return FBSFeedback.SUCCESS;
        } catch (Exception e) {
            System.out.println(e.toString());
            Util.transactionCommit();
            return FBSFeedback.UNKNOWN_ERROR;
        }
    }

}