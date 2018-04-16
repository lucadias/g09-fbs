package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * EmployeeDTO
 *
 * @author Mischa Gruber
 */
public final class EmployeeDTO implements Serializable {

    private int id;
    private String surname;
    private String firstname;
    private String username;
    private String password;
    private boolean active;

    public EmployeeDTO(final int id) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }
}
