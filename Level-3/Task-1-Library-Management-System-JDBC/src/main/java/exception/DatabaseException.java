package exception;

/**
 * Exception thrown when a database operation fails.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class DatabaseException extends Exception {

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message detail about the database failure
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and underlying cause.
     *
     * @param message detail about the failure
     * @param cause   the original exception
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
