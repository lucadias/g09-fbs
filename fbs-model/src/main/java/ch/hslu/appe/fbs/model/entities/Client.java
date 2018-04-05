package ch.hslu.appe.fbs.model.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Client {
    private int idClients;
    private String surname;
    private String firstname;
    private String adress;
    private Boolean active;

    @Id
    @Column(name = "idClients", nullable = false)
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
    @Column(name = "Adress", nullable = true, length = 200)
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
                Objects.equals(adress, client.adress) &&
                Objects.equals(active, client.active);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idClients, surname, firstname, adress, active);
    }
}
