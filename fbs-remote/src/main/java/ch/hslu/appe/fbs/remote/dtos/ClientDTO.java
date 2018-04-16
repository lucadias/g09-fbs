package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * ClientDTO
 *
 * @author Mischa Gruber
 */
public final class ClientDTO implements Serializable {

    private int id;
    private String surname;
    private String firstname;
    private String address;
    private boolean active;

    public ClientDTO(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }
}
