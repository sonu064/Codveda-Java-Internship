package util;

public final class AccountNumberGenerator {

    private static final String ACCOUNT_PREFIX = "ACC";
    private static final int STARTING_NUMBER = 1001;

    private static int counter = STARTING_NUMBER;


    private AccountNumberGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }


    public static synchronized String generateAccountNumber() {
        String accountNumber = ACCOUNT_PREFIX + counter;
        counter++;
        return accountNumber;
    }
}
