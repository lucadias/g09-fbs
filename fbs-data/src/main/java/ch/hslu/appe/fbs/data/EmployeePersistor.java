package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Employee;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;


//Hotfix


/**
 * JavaDoc
 *
 * @author Pascal Stalder
 */
public class EmployeePersistor {

    private final EntityManager entitymanager = Util.entityManager;

    public Employee getById(int id) {
        Util.transactionBegin();

        Employee employee = entitymanager.find(Employee.class, id);


        //code

        Util.transactionCommit();

        return employee;
    }

    public Employee getByUserName(String username){
        return (Employee) this.entitymanager.createQuery("SELECT c FROM Employee c WHERE c.username LIKE :custName")
                .setParameter("custName", username)
                .setMaxResults(1)
                .getSingleResult();
    }

    public Employee getByemployeeNr(int employeeNr) { return this.getById(employeeNr);}

    public FBSFeedback save(Employee employee) { return Util.save(employee); }

    public List<Employee> getList() {
        return this.entitymanager.createQuery("Select a From Employee a").getResultList();
    }

    public List<Employee> getList(String regEx) {
        String query = "";
        try {
            int regexint = Integer.parseInt(regEx);
            query = "SELECT c FROM Employee c WHERE c.idEmployees  LIKE :regexint";
        }catch (NumberFormatException nfe) {
            query = "SELECT c FROM Employee c WHERE c.firstname LIKE :regex OR c.surname LIKE :regex OR c.username LIKE :regex";
        } finally {
            return this.entitymanager.createQuery(query)
                    .setParameter("regex", regEx)
                    .getResultList();
        }
    }


}