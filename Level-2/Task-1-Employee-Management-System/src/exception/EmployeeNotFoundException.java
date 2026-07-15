package exception;

/**
 * Exception thrown when a requested employee cannot be found in the system.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class EmployeeNotFoundException extends Exception {

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message detail about the missing employee
     */
    public EmployeeNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an exception for a missing employee ID.
     *
     * @param employeeId the ID that was not found
     * @return a new {@link EmployeeNotFoundException}
     */
    public static EmployeeNotFoundException forId(String employeeId) {
        return new EmployeeNotFoundException("Employee Not Found with ID: " + employeeId);
    }
}
