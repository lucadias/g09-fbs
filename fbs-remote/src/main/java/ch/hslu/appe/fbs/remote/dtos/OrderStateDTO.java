package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * OrderStateDTO
 *
 * @author Mischa Gruber
 */
public final class OrderStateDTO implements Serializable {

    private int id;
    private String state;

    public OrderStateDTO(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }
}
