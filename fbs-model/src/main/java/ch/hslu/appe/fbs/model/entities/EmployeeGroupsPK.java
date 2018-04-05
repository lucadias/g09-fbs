package ch.hslu.appe.fbs.model.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EmployeeGroupsPK implements Serializable {
    private int employeeIdEmployees;
    private int groupsIdGroups;

    @Column(name = "Employee_idEmployees", nullable = false)
    @Id
    public int getEmployeeIdEmployees() {
        return employeeIdEmployees;
    }

    public void setEmployeeIdEmployees(int employeeIdEmployees) {
        this.employeeIdEmployees = employeeIdEmployees;
    }

    @Column(name = "Groups_idGroups", nullable = false)
    @Id
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
        EmployeeGroupsPK that = (EmployeeGroupsPK) o;
        return employeeIdEmployees == that.employeeIdEmployees &&
                groupsIdGroups == that.groupsIdGroups;
    }

    @Override
    public int hashCode() {

        return Objects.hash(employeeIdEmployees, groupsIdGroups);
    }
}
