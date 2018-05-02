package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.OrderState;
import ch.hslu.appe.fbs.model.entities.OrderedArticles;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderStatePersistor {
    private final EntityManager entitymanager = Util.entityManager;


    public OrderState getById(int id) {
        Util.transactionBegin();

        OrderState state = entitymanager.find(OrderState.class, id);

        Util.transactionCommit();

        return state;
    }

    public FBSFeedback save(OrderState state) {
        return Util.save(state);
    }

    public List<OrderState> getList() {
        Util.transactionBegin();
        List<OrderState> list = this.entitymanager.createQuery("Select s From OrderState s").getResultList();
        Util.transactionCommit();
        return list;
    }
}
