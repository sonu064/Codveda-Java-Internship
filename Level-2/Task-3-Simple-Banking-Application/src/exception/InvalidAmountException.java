package exception;


public class InvalidAmountException extends Exception {

    public InvalidAmountException(String message) {
        super(message);
    }


    public static InvalidAmountException nonPositive(double amount) {
        return new InvalidAmountException(
                "Amount must be greater than zero. Provided: " + amount);
    }
}
