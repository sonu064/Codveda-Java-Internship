package util;

/**
 * Utility class for generating unique bank account numbers.
 * <p>
 * Produces sequential account numbers in the format {@code ACC1001}, {@code ACC1002}, etc.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class AccountNumberGenerator {

    private static final String ACCOUNT_PREFIX = "ACC";
    private static final int STARTING_NUMBER = 1001;

    private static int counter = STARTING_NUMBER;

    /**
     * Private constructor to prevent instantiation.
     */
    private AccountNumberGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Generates the next unique account number.
     *
     * @return a new account number string
     */
    public static synchronized String generateAccountNumber() {
        String accountNumber = ACCOUNT_PREFIX + counter;
        counter++;
        return accountNumber;
    }
}
