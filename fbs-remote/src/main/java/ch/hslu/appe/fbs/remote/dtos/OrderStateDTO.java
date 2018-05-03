package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * DTO of OrderState Entity.
 *
 * @author Mischa Gruber
 */
public final class OrderStateDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private int id;
    private String state;

    /**
     * Constructor of the OrderStateDTO.
     * The id has to be given, because there is no setter method
     * for the id.
     * @param id the id to set
     */
    public OrderStateDTO(final int id) {
        this.id = id;
    }

    /**
     * Returns the database id of the order state.
     * @return id of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the state name of the OrderState.
     * @return state name of the OrderState
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state name of the OrderState.
     * @param state state name to set
     */
    public void setState(final String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        OrderStateDTO orderStateDTO = (OrderStateDTO) obj;

        return (orderStateDTO.getId() == this.id);
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
