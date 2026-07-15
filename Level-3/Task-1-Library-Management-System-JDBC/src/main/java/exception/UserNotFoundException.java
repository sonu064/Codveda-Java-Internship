package exception;


public class UserNotFoundException extends Exception {


    public UserNotFoundException(String message) {
        super(message);
    }


    public static UserNotFoundException forId(int userId) {
        return new UserNotFoundException("User Not Found with ID: " + userId);
    }
}
