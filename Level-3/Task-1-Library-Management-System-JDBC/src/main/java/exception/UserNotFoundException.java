package exception;

/**
 * Exception thrown when a requested user cannot be found.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class UserNotFoundException extends Exception {

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message detail about the missing user
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an exception for a missing user ID.
     *
     * @param userId the user ID that was not found
     * @return a new {@link UserNotFoundException}
     */
    public static UserNotFoundException forId(int userId) {
        return new UserNotFoundException("User Not Found with ID: " + userId);
    }
}
