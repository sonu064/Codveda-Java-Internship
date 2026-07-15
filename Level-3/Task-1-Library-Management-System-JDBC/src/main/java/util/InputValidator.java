package util;

/**
 * Utility class for validating library system user input.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class InputValidator {

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 11;
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
     * @param option menu option
     * @return {@code true} if between 1 and 11
     */
    public static boolean isValidMenuOption(int option) {
        return option >= MIN_MENU_OPTION && option <= MAX_MENU_OPTION;
    }

    /**
     * Validates that a string is not blank.
     *
     * @param value the value
     * @return {@code true} if non-empty
     */
    public static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

    /**
     * Validates email format.
     *
     * @param email email address
     * @return {@code true} if valid
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }

    /**
     * Validates a 10-digit phone number.
     *
     * @param phone phone number
     * @return {@code true} if valid
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches(PHONE_PATTERN);
    }

    /**
     * Validates that a quantity is positive.
     *
     * @param quantity the quantity
     * @return {@code true} if greater than zero
     */
    public static boolean isPositiveQuantity(int quantity) {
        return quantity > 0;
    }

    /**
     * Validates that an ID is positive.
     *
     * @param id the ID value
     * @return {@code true} if positive
     */
    public static boolean isPositiveId(int id) {
        return id > 0;
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
