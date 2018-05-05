package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * DTO of Employee Entity.
 *
 * @author Mischa Gruber
 */
public final class EmployeeDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private int id;
    private String surname;
    private String firstname;
    private String username;
    private String password;
    private boolean active;

    /**
     * Constructor of the EmployeeDTO.
     * The id has to be given, because there is no setter method
     * for the id.
     * @param id the id to set
     */
    public EmployeeDTO(final int id) {
        this.id = id;
    }

    /**
     * Returns the database id of the employee.
     * @return id of the employee
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the surname / lastname of the employee.
     * @return surname of the employee
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname / lastname of the employee.
     * @param surname the surname to set
     */
    public void setSurname(final String surname) {
        this.surname = surname;
    }

    /**
     * Returns the firstname of the employee.
     * @return firstname of the empyoyee
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname of the employee.
     * @param firstname the firstname to set
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the username of the employee for the login.
     * @return username of the employee
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the employee for the login.
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Returns the password <strong>hash</strong> of the employee.
     * @return password hash of the employee
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password <strong>hash</strong> for the employee.
     * @param password the password hash to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Returns if the employee is active / can login.
     * @return if the employee is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets if the employee is active / can login.
     * @param active the boolean to set
     */
    public void setActive(final boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        EmployeeDTO employeeDTO= (EmployeeDTO) obj;

        return (employeeDTO.getId() == this.id);
    }

    @Override
    public int hashCode() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return this.getUsername();
    }
}
