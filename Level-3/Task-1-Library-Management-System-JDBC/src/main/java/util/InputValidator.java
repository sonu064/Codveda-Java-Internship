package util;

public final class InputValidator {

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 11;
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


    public static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches(PHONE_PATTERN);
    }

    public static boolean isPositiveQuantity(int quantity) {
        return quantity > 0;
    }


    public static boolean isPositiveId(int id) {
        return id > 0;
    }


    public static int getPhoneNumberLength() {
        return PHONE_NUMBER_LENGTH;
    }
}
