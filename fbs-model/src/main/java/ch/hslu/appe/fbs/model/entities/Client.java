package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Client")
public class Client {
    private int idClients;
    private String surname;
    private String firstname;
    private String address;
    private Boolean active;

    @Id
    @Column(name = "idClients", nullable = false)
    @GeneratedValue()
    public int getIdClients() {
        return idClients;
    }

    public void setIdClients(int idClients) {
        this.idClients = idClients;
    }

    @Basic
    @Column(name = "Surname", nullable = true, length = 45)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "Firstname", nullable = true, length = 45)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "Address", nullable = true, length = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "Active", nullable = true)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return idClients == client.idClients &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(firstname, client.firstname) &&
                Objects.equals(address, client.address) &&
                Objects.equals(active, client.active);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idClients, surname, firstname, address, active);
    }
}
