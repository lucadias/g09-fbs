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

    private final EntityManager entitymanager = DBEntityManager.em;

    public Employee getById(int id) {
        transactionBegin();

        Employee employee = entitymanager.find(Employee.class, id);


        //code

        transactionClose();

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

    public List<Employee> search(String regEx) {
        return getList();
    }

    private void transactionBegin(){
        entitymanager.getTransaction().begin();

    }

    private void transactionClose(){
        entitymanager.getTransaction().commit();

    }





    //entitymanager.persist( employee );
    //entitymanager.getTransaction( ).commit( );


}