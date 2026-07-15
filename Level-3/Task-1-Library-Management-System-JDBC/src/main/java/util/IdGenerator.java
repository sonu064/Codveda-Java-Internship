package util;

/**
 * Utility class for generating formatted reference identifiers.
 * <p>
 * Used for human-readable transaction reference codes in console output.
 * Primary keys are managed by the database AUTO_INCREMENT.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class IdGenerator {

    private static final String TRANSACTION_PREFIX = "TXN";

    private static int transactionCounter = 1000;

    /**
     * Private constructor to prevent instantiation.
     */
    private IdGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Generates a formatted transaction reference code.
     *
     * @return reference code such as {@code TXN1001}
     */
    public static synchronized String generateTransactionReference() {
        String reference = TRANSACTION_PREFIX + transactionCounter;
        transactionCounter++;
        return reference;
    }
}
