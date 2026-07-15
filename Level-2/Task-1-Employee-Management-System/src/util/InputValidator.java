package util;

/**
 * Utility class for validating employee-related user input.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class InputValidator {

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 7;

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 65;

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
     * @return {@code true} if valid
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
     * Validates employee age within acceptable working range.
     *
     * @param age the age value
     * @return {@code true} if age is between 18 and 65
     */
    public static boolean isValidAge(int age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }

    /**
     * Validates that salary is non-negative.
     *
     * @param salary the salary amount
     * @return {@code true} if salary is zero or positive
     */
    public static boolean isValidSalary(double salary) {
        return salary >= 0;
    }

    /**
     * Validates email format using a standard regex pattern.
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
     * Validates gender input (Male, Female, or Other — case-insensitive).
     *
     * @param gender the gender string
     * @return {@code true} if valid
     */
    public static boolean isValidGender(String gender) {
        if (gender == null || gender.isBlank()) {
            return false;
        }
        String normalized = gender.trim().toUpperCase();
        return normalized.equals("MALE") || normalized.equals("FEMALE") || normalized.equals("OTHER")
                || normalized.equals("M") || normalized.equals("F") || normalized.equals("O");
    }

    /**
     * Normalizes gender to a standard display format.
     *
     * @param gender raw gender input
     * @return normalized gender string
     */
    public static String normalizeGender(String gender) {
        String normalized = gender.trim().toUpperCase();
        return switch (normalized) {
            case "M", "MALE" -> "Male";
            case "F", "FEMALE" -> "Female";
            default -> "Other";
        };
    }

    /**
     * Validates that department is not empty.
     *
     * @param department the department name
     * @return {@code true} if non-empty
     */
    public static boolean isValidDepartment(String department) {
        return department != null && !department.isBlank();
    }

    /**
     * Validates that designation is not empty.
     *
     * @param designation the job designation
     * @return {@code true} if non-empty
     */
    public static boolean isValidDesignation(String designation) {
        return designation != null && !designation.isBlank();
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
     * Returns the minimum allowed age.
     *
     * @return minimum age
     */
    public static int getMinAge() {
        return MIN_AGE;
    }

    /**
     * Returns the maximum allowed age.
     *
     * @return maximum age
     */
    public static int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Returns the required phone number length.
     *
     * @return phone number digit count
     */
    public static int getPhoneNumberLength() {
        return PHONE_NUMBER_LENGTH;
    }
}
