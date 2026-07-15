package exception;

/**
 * Exception thrown when an account has insufficient balance for a withdrawal or transfer.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class InsufficientBalanceException extends Exception {

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message detail about the insufficient balance
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }

    /**
     * Creates an exception for a specific account and requested amount.
     *
     * @param accountNumber the account number
     * @param balance       current balance
     * @param amount        requested amount
     * @return a new {@link InsufficientBalanceException}
     */
    public static InsufficientBalanceException forAccount(String accountNumber,
                                                          double balance, double amount) {
        return new InsufficientBalanceException(String.format(
                "Insufficient balance in account %s. Available: %.2f, Requested: %.2f",
                accountNumber, balance, amount));
    }
}
