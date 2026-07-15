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

    /**
     * Private constructor to prevent instantiation.
     */
    private InputValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Validates a main menu option.
     *
     * @param option the menu option
     * @return {@code true} if valid (1–9)
     */
    public static boolean isValidMenuOption(int option) {
        return option >= MIN_MENU_OPTION && option <= MAX_MENU_OPTION;
    }

    /**
     * Validates that a name is not null or blank.
     *
     * @param name the name to validate
     * @return {@code true} if non-empty
     */
    public static boolean isValidName(String name) {
        return name != null && !name.isBlank();
    }

    /**
     * Validates email format.
     *
     * @param email the email address
     * @return {@code true} if format is valid
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }

    /**
     * Validates phone number as exactly 10 digits.
     *
     * @param phoneNumber the phone number
     * @return {@code true} if valid
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_PATTERN);
    }

    /**
     * Validates that an amount is positive.
     *
     * @param amount the monetary amount
     * @return {@code true} if amount is greater than zero
     */
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    /**
     * Validates that an initial balance is non-negative.
     *
     * @param balance the initial balance
     * @return {@code true} if zero or positive
     */
    public static boolean isValidInitialBalance(double balance) {
        return balance >= 0;
    }

    /**
     * Validates a Y/N confirmation response.
     *
     * @param response user response
     * @return {@code true} if Y or N
     */
    public static boolean isValidConfirmation(String response) {
        if (response == null || response.isBlank()) {
            return false;
        }
        String normalized = response.trim().toUpperCase();
        return normalized.equals("Y") || normalized.equals("N");
    }

    /**
     * Returns whether the user confirmed with Y.
     *
     * @param response user response
     * @return {@code true} if confirmed
     */
    public static boolean isConfirmed(String response) {
        return response.trim().toUpperCase().equals("Y");
    }

    /**
     * Returns the required phone number length.
     *
     * @return digit count
     */
    public static int getPhoneNumberLength() {
        return PHONE_NUMBER_LENGTH;
    }
}
