package util;

public final class IdGenerator {

    private static final String TRANSACTION_PREFIX = "TXN";

    private static int transactionCounter = 1000;

    private IdGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    public static synchronized String generateTransactionReference() {
        String reference = TRANSACTION_PREFIX + transactionCounter;
        transactionCounter++;
        return reference;
    }
}
