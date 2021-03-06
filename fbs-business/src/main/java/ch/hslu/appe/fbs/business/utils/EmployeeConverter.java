package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.Employee;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter for employee entity and DTO.
 *
 * @author Mischa Gruber
 */
public final class EmployeeConverter {

    /**
     * Converts an employee entity into a DTO.
     * @param employee employee to be converted
     * @return converted employee
     */
    public EmployeeDTO convertToDTO(final Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getIdEmployees());
        employeeDTO.setSurname(employee.getSurname());
        employeeDTO.setFirstname(employee.getFirstname());
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setActive(employee.getActive());

        return employeeDTO;
    }

    /**
     * Converts a list of employee entities into DTOs.
     * @param employeeList list to be converted
     * @return converted list
     */
    public List<EmployeeDTO> convertToDTO(final List<Employee> employeeList) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeDTOList.add(convertToDTO(employee));
        }

        return employeeDTOList;
    }

    /**
     * Converts an employee DTO into an entity.
     * @param employeeDTO employee to be converted
     * @return converted employee
     */
    public Employee convertToEntity(final EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        if (employeeDTO.getId() != -1) {
            employee.setIdEmployees(employeeDTO.getId());
        }
        employee.setSurname(employeeDTO.getSurname());
        employee.setFirstname(employeeDTO.getFirstname());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setActive(employeeDTO.isActive());

        return employee;
    }
}
