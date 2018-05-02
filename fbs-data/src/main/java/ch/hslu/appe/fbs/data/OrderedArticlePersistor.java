package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.OrderedArticles;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderedArticlePersistor {
    private final EntityManager entitymanager = Util.entityManager;


    public OrderedArticles getById(int id) {
        Util.transactionBegin();

        OrderedArticles article = entitymanager.find(OrderedArticles.class, id);

        Util.transactionCommit();

        return article;
    }

    public OrderedArticles getByArticleNr(int artNr) { return this.getById(artNr);}

    public FBSFeedback save(OrderedArticles article) {
        return Util.save(article);
    }

    public List<OrderedArticles> getList() {
        Util.transactionBegin();
        List<OrderedArticles> list = this.entitymanager.createQuery("Select o From OrderedArticles o").getResultList();
        Util.transactionCommit();
        return list;
    }
}
