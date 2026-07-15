package util;

/**
 * Utility class for generating unique employee identifiers.
 * <p>
 * Produces sequential IDs in the format {@code EMP001}, {@code EMP002}, etc.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class IdGenerator {

    private static final String ID_PREFIX = "EMP";
    private static final int ID_PADDING = 3;

    private static int counter = 1;

    /**
     * Private constructor to prevent instantiation.
     */
    private IdGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Generates the next unique employee ID.
     *
     * @return a new employee ID string
     */
    public static synchronized String generateEmployeeId() {
        String id = String.format("%s%0" + ID_PADDING + "d", ID_PREFIX, counter);
        counter++;
        return id;
    }

    /**
     * Returns the current counter value (for testing purposes).
     *
     * @return next ID number that will be assigned
     */
    public static int getCurrentCounter() {
        return counter;
    }
}
