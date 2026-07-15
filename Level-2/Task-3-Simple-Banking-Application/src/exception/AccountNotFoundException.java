package exception;


public class AccountNotFoundException extends Exception {


    public AccountNotFoundException(String message) {
        super(message);
    }


    public static AccountNotFoundException forAccountNumber(String accountNumber) {
        return new AccountNotFoundException("Account Not Found: " + accountNumber);
    }
}
