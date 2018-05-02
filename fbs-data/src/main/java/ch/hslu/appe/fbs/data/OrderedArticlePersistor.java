package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.OrderedArticles;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderedArticlePersistor {
    private final EntityManager entitymanager = DBEntityManager.em;


    public OrderedArticles getById(int id) {
        transactionBegin();

        OrderedArticles article = entitymanager.find(OrderedArticles.class, id);

        transactionCommit();

        return article;
    }

    public OrderedArticles getByArticleNr(int artNr) { return this.getById(artNr);}

    public FBSFeedback save(OrderedArticles article) {
        return Util.save(article);
    }

    public List<OrderedArticles> getList() {
        this.transactionBegin();
        List<OrderedArticles> list = this.entitymanager.createQuery("Select o From OrderedArticles o").getResultList();
        this.transactionCommit();
        return list;
    }

    private void transactionBegin(){
        entitymanager.getTransaction().begin();
    }

    private void transactionCommit(){
        entitymanager.getTransaction().commit();
    }
}
