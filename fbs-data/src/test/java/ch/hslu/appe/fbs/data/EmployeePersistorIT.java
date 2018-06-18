package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Employee;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeePersistorIT {

    private EmployeePersistor persistor = new EmployeePersistor();
    private Employee employee;


    @Test
    public void testGetById() {
        assertEquals(7, persistor.getById(7).getIdEmployees());
    }

    @Test
    public void testGetByUserName() {
        assertEquals("luca", persistor.getByUserName("luca").getUsername());
    }


}
