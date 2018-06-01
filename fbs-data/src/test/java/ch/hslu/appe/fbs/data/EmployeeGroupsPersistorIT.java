package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.EmployeeGroups;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmployeeGroupsPersistorIT {

    private EmployeeGroupsPersistor persistor = new EmployeeGroupsPersistor();
    private EmployeeGroups employeeGroups;

    @Before
    public void setup() {

    }


    //INSERTS IN DB
    //  Employee_idEmployees     |   Groups_idGroups
    //  7	                     |   4  & 3
    //  8	                     |   3
    //  9	                     |   2
    //  10	                     |   1


    @Test
    public void getEmployeeGroupsByEmployeeIdTest(){


        assertEquals(4,persistor.getEmployeeGroupsByEmployeeId(7).get(1).getGroupsIdGroups());

        assertEquals(3,persistor.getEmployeeGroupsByEmployeeId(7).get(0).getGroupsIdGroups());

        assertEquals(3,persistor.getEmployeeGroupsByEmployeeId(8).get(0).getGroupsIdGroups());

        assertEquals(2,persistor.getEmployeeGroupsByEmployeeId(9).get(0).getGroupsIdGroups());

        assertEquals(1,persistor.getEmployeeGroupsByEmployeeId(10).get(0).getGroupsIdGroups());


    }


    @Test
    public void getEmployeeGroupsByGroupsIdTest(){

        assertEquals(10,persistor.getEmployeeGroupsByGroupId(1).get(0).getEmployeeIdEmployees());

        assertEquals(9,persistor.getEmployeeGroupsByGroupId(2).get(0).getEmployeeIdEmployees());

        assertEquals(8,persistor.getEmployeeGroupsByGroupId(3).get(1).getEmployeeIdEmployees());
        assertEquals(7,persistor.getEmployeeGroupsByGroupId(3).get(0).getEmployeeIdEmployees());

        assertEquals(7,persistor.getEmployeeGroupsByGroupId(4).get(0).getEmployeeIdEmployees());



    }
}