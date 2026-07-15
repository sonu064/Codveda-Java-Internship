package model;

import java.util.Locale;
import java.util.Objects;


public class Employee {

    private String employeeId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String department;
    private String designation;
    private double salary;
    private String email;
    private String phoneNumber;


    public Employee() {

    }


    public Employee(String employeeId, String firstName, String lastName, int age,
                    String gender, String department, String designation,
                    double salary, String email, String phoneNumber) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Employee(String firstName, String lastName, int age, String gender,
                    String department, String designation, double salary,
                    String email, String phoneNumber) {
        this(null, firstName, lastName, age, gender, department, designation, salary, email, phoneNumber);
    }


    public String getEmployeeId() {
        return employeeId;
    }


    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalary() {
        return salary;
    }


    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

 
    @Override
    public String toString() {
        return String.format(Locale.US,
                "ID: %s | Name: %s | Age: %d | Gender: %s | Dept: %s | Designation: %s | "
                        + "Salary: %.2f | Email: %s | Phone: %s",
                employeeId, getFullName(), age, gender, department, designation, salary, email, phoneNumber);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Employee employee = (Employee) object;
        return Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}
