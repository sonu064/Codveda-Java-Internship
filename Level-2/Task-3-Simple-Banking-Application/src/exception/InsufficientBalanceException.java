package exception;


public class InsufficientBalanceException extends Exception {


    public InsufficientBalanceException(String message) {
        super(message);
    }


    public static InsufficientBalanceException forAccount(String accountNumber,
                                                          double balance, double amount) {
        return new InsufficientBalanceException(String.format(
                "Insufficient balance in account %s. Available: %.2f, Requested: %.2f",
                accountNumber, balance, amount));
    }
}
