package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.Employee;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;

/**
 * EmployeeConverter
 *
 * @author Mischa Gruber
 */
public final class EmployeeConverter {

    public EmployeeDTO convertToDTO(final Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getIdEmployees());
        employeeDTO.setSurname(employee.getSurname());
        employeeDTO.setFirstname(employee.getFirstname());
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setActive(employee.getActive());

        return employeeDTO;
    }

    public Employee convertToEntity(final EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setIdEmployees(employeeDTO.getId());
        employee.setSurname(employeeDTO.getSurname());
        employee.setFirstname(employeeDTO.getFirstname());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setActive(employeeDTO.isActive());

        return employee;
    }
}
