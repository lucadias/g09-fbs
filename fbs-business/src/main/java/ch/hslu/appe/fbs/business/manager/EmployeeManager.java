package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.EmployeeConverter;
import ch.hslu.appe.fbs.data.EmployeePersistor;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;

import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class EmployeeManager {
    private static EmployeeManager instance = null;

    private static Object mutex = new Object();

    private EmployeePersistor employeePersistor;
    private EmployeeConverter employeeConverter;

    /**
     * Returns the singleton instance of the EmployeeManager.
     * @return single instance
     */
    public static EmployeeManager getInstance() {
        EmployeeManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new EmployeeManager();
                }
            }
        }
        return result;
    }

    /**
     * Private constructor for the single pattern.
     */
    private EmployeeManager() {
        this.employeePersistor = new EmployeePersistor();
        this.employeeConverter = new EmployeeConverter();
    }

    public EmployeeDTO getById(final String sessionId, final int id) {
        return employeeConverter.convertToDTO(employeePersistor.getById(id));
    }

    public List<EmployeeDTO> getList(final String sessionId) {
        return employeeConverter.convertToDTO(employeePersistor.getList());
    }
}
