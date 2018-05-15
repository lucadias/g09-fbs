package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.OrderState;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Class for persisting OrderStates
 *
 * @author Pascal Stalder
 */
public class OrderStatePersistor {
    private final EntityManager entitymanager = Util.entityManager;


    /**
     * Returns an {@link OrderState} by its id
     * @param id int
     * @return {@link OrderState}
     */
    public OrderState getById(int id) {
        Util.transactionBegin();

        OrderState state = entitymanager.find(OrderState.class, id);

        Util.transactionCommit();

        return state;
    }

    /**
     * Saves or updates an OrderState.
     * @param state {@link OrderState}
     * @return {@link FBSFeedback}
     */
    public FBSFeedback save(OrderState state) {
        return Util.save(state);
    }

    /**
     * Returns all possible OrderStates
     * @return List&gt;{@link OrderState}&lt;
     */
    public List<OrderState> getList() {
        Util.transactionBegin();
        List<OrderState> list = this.entitymanager.createQuery("Select s From OrderState s").getResultList();
        Util.transactionCommit();
        return list;
    }
}
