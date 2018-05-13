package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.OrderedArticles;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Class for persistiong OrderedArticles.
 *
 * @author Pascal Stalder
 */
public class OrderedArticlePersistor {
    private final EntityManager entitymanager = Util.entityManager;

    /**
     * Returns an OrderedArticle by its id.
     * @param id int
     * @return OrderedArticle
     */
    public OrderedArticles getById(int id) {
        Util.transactionBegin();

        OrderedArticles article = entitymanager.find(OrderedArticles.class, id);

        Util.transactionCommit();

        return article;
    }

    /**
     * Returns a List of all OrderedArticles from specified Order.
     * @param id int idOrder
     * @return List&gt;OrderedArticle&lt;
     */
    public List<OrderedArticles> getByOrderId(int id) {
        Util.transactionBegin();

        List<OrderedArticles> orderedArticlesList = this.entitymanager.createQuery("SELECT o FROM OrderedArticles o WHERE o.ordersIdOrder LIKE :orderId")
                .setParameter("orderId", id)
                .getResultList();

        Util.transactionCommit();

        return orderedArticlesList;
    }

    /**
     * Returns an OrderedArticle by its article number.
     * @param artNr int
     * @return OrderedArticle
     */
    public OrderedArticles getByArticleNr(int artNr) { return this.getById(artNr);}

    /**
     * Saves or updates an OrderedArticle.
     * @param article OrderedArticle
     * @return FBSFeedback
     */
    public FBSFeedback save(OrderedArticles article) {
        return Util.save(article);
    }

    /**
     * Returns a List containing every OrderedArticle.
     * @return List&gt;OrderedArticle&lt;
     */
    public List<OrderedArticles> getList() {
        Util.transactionBegin();
        List<OrderedArticles> list = this.entitymanager.createQuery("Select o From OrderedArticles o").getResultList();
        Util.transactionCommit();
        return list;
    }
}
