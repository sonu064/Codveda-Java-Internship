package util;

/**
 * Utility class for validating banking-related user input.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class InputValidator {

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 9;

    private static final int PHONE_NUMBER_LENGTH = 10;

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final String PHONE_PATTERN = "^[0-9]{10}$";


    private InputValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }


    public static boolean isValidMenuOption(int option) {
        return option >= MIN_MENU_OPTION && option <= MAX_MENU_OPTION;
    }


    public static boolean isValidName(String name) {
        return name != null && !name.isBlank();
    }


    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }


    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_PATTERN);
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }


    public static boolean isValidInitialBalance(double balance) {
        return balance >= 0;
    }


    public static boolean isValidConfirmation(String response) {
        if (response == null || response.isBlank()) {
            return false;
        }
        String normalized = response.trim().toUpperCase();
        return normalized.equals("Y") || normalized.equals("N");
    }


    public static boolean isConfirmed(String response) {
        return response.trim().toUpperCase().equals("Y");
    }

    public static int getPhoneNumberLength() {
        return PHONE_NUMBER_LENGTH;
    }
}
