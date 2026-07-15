package exception;

/**
 * Exception thrown when a monetary amount is invalid (negative or zero).
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class InvalidAmountException extends Exception {

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message detail about the invalid amount
     */
    public InvalidAmountException(String message) {
        super(message);
    }

    /**
     * Creates an exception for a non-positive amount.
     *
     * @param amount the invalid amount
     * @return a new {@link InvalidAmountException}
     */
    public static InvalidAmountException nonPositive(double amount) {
        return new InvalidAmountException(
                "Amount must be greater than zero. Provided: " + amount);
    }
}
