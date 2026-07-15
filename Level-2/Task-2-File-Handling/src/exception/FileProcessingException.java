package exception;

/**
 * Exception thrown when file reading, processing, or writing fails.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class FileProcessingException extends Exception {

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message detail about the failure
     */
    public FileProcessingException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and underlying cause.
     *
     * @param message detail about the failure
     * @param cause   the original exception
     */
    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
