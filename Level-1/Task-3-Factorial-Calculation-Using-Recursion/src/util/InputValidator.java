package util;

/**
 * Utility class for validating user input in the Factorial Calculator application.
 * <p>
 * Centralizes validation rules to keep presentation and business logic decoupled.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class InputValidator {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private InputValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Validates whether a number is non-negative (valid for factorial calculation).
     *
     * @param number the number to validate
     * @return {@code true} if the number is zero or positive
     */
    public static boolean isNonNegative(int number) {
        return number >= 0;
    }

    /**
     * Validates whether the user's continue response is Y or N (case-insensitive).
     *
     * @param response the user's response string
     * @return {@code true} if the response is Y or N
     */
    public static boolean isValidContinueResponse(String response) {
        if (response == null || response.isBlank()) {
            return false;
        }
        String normalized = response.trim().toUpperCase();
        return normalized.equals("Y") || normalized.equals("N");
    }

    /**
     * Determines whether the user wants to calculate another factorial.
     *
     * @param response the user's validated Y/N response
     * @return {@code true} if the response is Y
     */
    public static boolean shouldContinue(String response) {
        return response.trim().toUpperCase().equals("Y");
    }
}
