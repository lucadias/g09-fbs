package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Employee;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Class for persisting Employees
 *
 * @author Pascal Stalder
 */
public class EmployeePersistor {

    private final EntityManager entitymanager = Util.entityManager;

    /**
     * Returns an Employee by its id.
     *
     * @param id int
     * @return Employee
     */
    public Employee getById(int id) {
        Util.transactionBegin();

        Employee employee = entitymanager.find(Employee.class, id);


        //code

        Util.transactionCommit();

        return employee;
    }

    /**
     * Returns an Employee by its username.
     *
     * @param username String
     * @return Employee
     */
    public Employee getByUserName(String username) {
        return (Employee) this.entitymanager.createQuery("SELECT c FROM Employee c WHERE c.username LIKE :custName")
                .setParameter("custName", username)
                .setMaxResults(1)
                .getSingleResult();
    }


    public Employee getByEmployeeNr(int employeeNr) {
        return this.getById(employeeNr);
    }

    /**
     * saves or updates an Employee.
     *
     * @param employee Employee
     * @return FBSFeedback
     */
    public FBSFeedback save(Employee employee) {
        return Util.save(employee);
    }

    /**
     * Returns all Employees.
     *
     * @return List&gt;Employee&lt;
     */
    public List<Employee> getList() {
        return this.entitymanager.createQuery("Select a From Employee a").getResultList();
    }

    /**
     * Returns a List of Employees resembling the searchText
     *
     * @param searchText String
     * @return List&gt;Employee&lt;
     */
    public List<Employee> getList(String searchText) {
        String regex = "%"+searchText+"%";
        //noinspection JpaQlInspection
        String query = "SELECT e FROM Employee e WHERE e.firstname LIKE :regex OR e.surname LIKE :regex OR e.idEmployees LIKE :regex";

        return this.entitymanager.createQuery(query)
                .setParameter("regex", regex)
                .getResultList();
    }
}