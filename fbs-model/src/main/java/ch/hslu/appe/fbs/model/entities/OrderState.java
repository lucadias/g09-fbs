package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * OrderState entity
 */
@Entity
@Table(name = "OrderState")
public class OrderState {
    private int idOrderState;
    private String state;

    @Id
    @Column(name = "idOrderState", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getIdOrderState() {
        return idOrderState;
    }

    public void setIdOrderState(int idOrderState) {
        this.idOrderState = idOrderState;
    }

    @Basic
    @Column(name = "State", nullable = true, length = 45)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderState that = (OrderState) o;
        return idOrderState == that.idOrderState &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idOrderState, state);
    }
}
