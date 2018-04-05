package ch.hslu.appe.fbs.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(EmployeeGroupsPK.class)
public class EmployeeGroups {
    private int employeeIdEmployees;
    private int groupsIdGroups;

    @Id
    @Column(name = "Employee_idEmployees", nullable = false)
    public int getEmployeeIdEmployees() {
        return employeeIdEmployees;
    }

    public void setEmployeeIdEmployees(int employeeIdEmployees) {
        this.employeeIdEmployees = employeeIdEmployees;
    }

    @Id
    @Column(name = "Groups_idGroups", nullable = false)
    public int getGroupsIdGroups() {
        return groupsIdGroups;
    }

    public void setGroupsIdGroups(int groupsIdGroups) {
        this.groupsIdGroups = groupsIdGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeGroups that = (EmployeeGroups) o;
        return employeeIdEmployees == that.employeeIdEmployees &&
                groupsIdGroups == that.groupsIdGroups;
    }

    @Override
    public int hashCode() {

        return Objects.hash(employeeIdEmployees, groupsIdGroups);
    }
}
