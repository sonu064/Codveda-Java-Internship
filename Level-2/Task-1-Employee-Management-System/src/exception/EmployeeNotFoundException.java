package exception;


public class EmployeeNotFoundException extends Exception {


    public EmployeeNotFoundException(String message) {
        super(message);
    }


    public static EmployeeNotFoundException forId(String employeeId) {
        return new EmployeeNotFoundException("Employee Not Found with ID: " + employeeId);
    }
}
