package ch.hslu.appe.fbs.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Groups")
public class Groups {
    private int idGroups;
    private String name;

    @Id
    @Column(name = "idGroups", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getIdGroups() {
        return idGroups;
    }

    public void setIdGroups(int idGroups) {
        this.idGroups = idGroups;
    }

    @Basic
    @Column(name = "Name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return idGroups == groups.idGroups &&
                Objects.equals(name, groups.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idGroups, name);
    }
}
