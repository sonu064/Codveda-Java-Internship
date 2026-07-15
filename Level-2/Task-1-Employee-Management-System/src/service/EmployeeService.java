package service;

import exception.EmployeeNotFoundException;
import model.Employee;
import util.IdGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;


public class EmployeeService {

    private final List<Employee> employees;


    public EmployeeService() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        if (isEmailDuplicate(employee.getEmail(), null)) {
            throw new IllegalArgumentException("Duplicate email address: " + employee.getEmail());
        }
        if (isPhoneDuplicate(employee.getPhoneNumber(), null)) {
            throw new IllegalArgumentException("Duplicate phone number: " + employee.getPhoneNumber());
        }

        employee.setEmployeeId(IdGenerator.generateEmployeeId());
        employees.add(employee);
    }

    public List<Employee> getAllEmployees() {
        return Collections.unmodifiableList(employees);
    }


    public int getEmployeeCount() {
        return employees.size();
    }


    public Employee findById(String employeeId) throws EmployeeNotFoundException {
        return findEmployeeById(employeeId)
                .orElseThrow(() -> EmployeeNotFoundException.forId(employeeId));
    }


    public List<Employee> searchById(String employeeId) {
        if (employeeId == null || employeeId.isBlank()) {
            return Collections.emptyList();
        }
        String query = employeeId.trim().toUpperCase();
        List<Employee> results = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getEmployeeId().toUpperCase().contains(query)) {
                results.add(employee);
            }
        }
        return results;
    }


    public List<Employee> searchByName(String name) {
        if (name == null || name.isBlank()) {
            return Collections.emptyList();
        }
        String query = name.trim().toLowerCase();
        List<Employee> results = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getFirstName().toLowerCase().contains(query)
                    || employee.getLastName().toLowerCase().contains(query)
                    || employee.getFullName().toLowerCase().contains(query)) {
                results.add(employee);
            }
        }
        return results;
    }


    public List<Employee> searchByDepartment(String department) {
        if (department == null || department.isBlank()) {
            return Collections.emptyList();
        }
        String query = department.trim().toLowerCase();
        List<Employee> results = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getDepartment().toLowerCase().contains(query)) {
                results.add(employee);
            }
        }
        return results;
    }


    public Employee updateEmployee(String employeeId, String firstName, String lastName,
                                   String department, String designation, double salary,
                                   String email, String phoneNumber) throws EmployeeNotFoundException {
        Employee employee = findById(employeeId);

        if (firstName != null && !firstName.isBlank()) {
            employee.setFirstName(firstName.trim());
        }
        if (lastName != null && !lastName.isBlank()) {
            employee.setLastName(lastName.trim());
        }
        if (department != null && !department.isBlank()) {
            employee.setDepartment(department.trim());
        }
        if (designation != null && !designation.isBlank()) {
            employee.setDesignation(designation.trim());
        }
        if (salary >= 0) {
            employee.setSalary(salary);
        }
        if (email != null && !email.isBlank()) {
            if (isEmailDuplicate(email, employeeId)) {
                throw new IllegalArgumentException("Duplicate email address: " + email);
            }
            employee.setEmail(email.trim());
        }
        if (phoneNumber != null && !phoneNumber.isBlank()) {
            if (isPhoneDuplicate(phoneNumber, employeeId)) {
                throw new IllegalArgumentException("Duplicate phone number: " + phoneNumber);
            }
            employee.setPhoneNumber(phoneNumber.trim());
        }

        return employee;
    }

    public Employee deleteEmployee(String employeeId) throws EmployeeNotFoundException {
        Employee employee = findById(employeeId);
        employees.remove(employee);
        return employee;
    }

    public double getHighestSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .max()
                .orElse(0.0);
    }


    public double getLowestSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .min()
                .orElse(0.0);
    }


    public double getAverageSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    public Map<String, Integer> getDepartmentWiseCount() {
        Map<String, Integer> departmentCount = new HashMap<>();
        for (Employee employee : employees) {
            String dept = employee.getDepartment();
            departmentCount.merge(dept, 1, Integer::sum);
        }
        return departmentCount;
    }

    public boolean hasEmployees() {
        return !employees.isEmpty();
    }

    private Optional<Employee> findEmployeeById(String employeeId) {
        if (employeeId == null || employeeId.isBlank()) {
            return Optional.empty();
        }
        String normalizedId = employeeId.trim().toUpperCase();
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equalsIgnoreCase(normalizedId)) {
                return Optional.of(employee);
            }
        }
        return Optional.empty();
    }

    private boolean isEmailDuplicate(String email, String excludeEmployeeId) {
        String normalizedEmail = email.trim().toLowerCase();
        for (Employee employee : employees) {
            if (excludeEmployeeId != null
                    && employee.getEmployeeId().equalsIgnoreCase(excludeEmployeeId)) {
                continue;
            }
            if (employee.getEmail().equalsIgnoreCase(normalizedEmail)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPhoneDuplicate(String phoneNumber, String excludeEmployeeId) {
        String normalizedPhone = phoneNumber.trim();
        for (Employee employee : employees) {
            if (excludeEmployeeId != null
                    && employee.getEmployeeId().equalsIgnoreCase(excludeEmployeeId)) {
                continue;
            }
            if (employee.getPhoneNumber().equals(normalizedPhone)) {
                return true;
            }
        }
        return false;
    }
}
