package model;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents an employee entity in the Employee Management System.
 * <p>
 * Encapsulates all employee attributes with validation-friendly accessors.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
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

    /**
     * Default constructor required for object creation before field assignment.
     */
    public Employee() {
        // Intentionally empty — fields set via setters or parameterized constructor.
    }

    /**
     * Creates an employee with all required fields.
     *
     * @param employeeId   unique employee identifier
     * @param firstName    employee first name
     * @param lastName     employee last name
     * @param age          employee age
     * @param gender       employee gender
     * @param department   department name
     * @param designation  job designation
     * @param salary       monthly salary
     * @param email        email address
     * @param phoneNumber  contact phone number
     */
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

    /**
     * Creates an employee without an ID (used before ID generation).
     *
     * @param firstName    employee first name
     * @param lastName     employee last name
     * @param age          employee age
     * @param gender       employee gender
     * @param department   department name
     * @param designation  job designation
     * @param salary       monthly salary
     * @param email        email address
     * @param phoneNumber  contact phone number
     */
    public Employee(String firstName, String lastName, int age, String gender,
                    String department, String designation, double salary,
                    String email, String phoneNumber) {
        this(null, firstName, lastName, age, gender, department, designation, salary, email, phoneNumber);
    }

    /**
     * Returns the employee ID.
     *
     * @return employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID.
     *
     * @param employeeId unique employee identifier
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Returns the first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the full name (first + last).
     *
     * @return full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Returns the employee age.
     *
     * @return age in years
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the employee age.
     *
     * @param age age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the gender.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     *
     * @param gender gender value
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the department.
     *
     * @return department name
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department.
     *
     * @param department department name
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Returns the designation.
     *
     * @return job designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Sets the designation.
     *
     * @param designation job designation
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * Returns the salary.
     *
     * @return monthly salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary.
     *
     * @param salary monthly salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Returns the email address.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number.
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber contact phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns a formatted single-line summary of the employee.
     *
     * @return formatted employee summary
     */
    @Override
    public String toString() {
        return String.format(Locale.US,
                "ID: %s | Name: %s | Age: %d | Gender: %s | Dept: %s | Designation: %s | "
                        + "Salary: %.2f | Email: %s | Phone: %s",
                employeeId, getFullName(), age, gender, department, designation, salary, email, phoneNumber);
    }

    /**
     * Compares employees by employee ID for equality.
     *
     * @param object the reference object
     * @return {@code true} if IDs match
     */
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

    /**
     * Returns hash code based on employee ID.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}
