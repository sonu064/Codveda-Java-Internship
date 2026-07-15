package exception;

/**
 * Exception thrown when a requested bank account cannot be found.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class AccountNotFoundException extends Exception {

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message detail about the missing account
     */
    public AccountNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an exception for a missing account number.
     *
     * @param accountNumber the account number that was not found
     * @return a new {@link AccountNotFoundException}
     */
    public static AccountNotFoundException forAccountNumber(String accountNumber) {
        return new AccountNotFoundException("Account Not Found: " + accountNumber);
    }
}
