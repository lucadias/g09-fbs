package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * DTO of Client Entity.
 *
 * @author Mischa Gruber
 */
public final class ClientDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private int id;
    private String surname;
    private String firstname;
    private String address;
    private boolean active;

    /**
     * Constructor of the ClientDTO.
     * The id has to be given, because there is no setter method
     * for the id.
     * @param id the id to set
     */
    public ClientDTO(final int id) {
        this.id = id;
    }

    /**
     * Returns the database id of the client.
     * @return id of the client
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the surname / lastname of the client.
     * @return surname of the client.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname / lastname of the client.
     * @param surname the surname to set
     */
    public void setSurname(final String surname) {
        this.surname = surname;
    }

    /**
     * Returns the firstname of the client.
     * @return firstname of the client
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname of the client.
     * @param firstname the firstname to set
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the full address of the client.
     * The address contains the street, house number, ZIP, city.
     * @return address of the client.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the full address of the client.
     * The address contains the street, house number, ZIP, city.
     * @param address the address to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Returns if the client is active / should be visible on the frontend.
     * @return if the client is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets if the client is active / should be visible on the frontend.
     * @param active the boolean to set
     */
    public void setActive(final boolean active) {
        this.active = active;
    }
}
