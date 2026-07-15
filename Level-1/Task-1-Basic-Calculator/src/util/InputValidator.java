package util;

/**
 * Utility class for validating user input in the Basic Calculator application.
 * <p>
 * Centralizes validation rules to keep presentation and business logic decoupled.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class InputValidator {

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 9;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private InputValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Validates whether the given value is a valid calculator menu option.
     *
     * @param option the menu option entered by the user
     * @return {@code true} if the option is between 1 and 9 (inclusive); {@code false} otherwise
     */
    public static boolean isValidMenuOption(int option) {
        return option >= MIN_MENU_OPTION && option <= MAX_MENU_OPTION;
    }

    /**
     * Validates whether a divisor is non-zero for division or modulus operations.
     *
     * @param divisor the divisor to validate
     * @return {@code true} if the divisor is not zero; {@code false} otherwise
     */
    public static boolean isValidDivisor(double divisor) {
        return divisor != 0.0;
    }

    /**
     * Validates whether a number is valid for square root computation.
     *
     * @param number the operand to validate
     * @return {@code true} if the number is zero or positive; {@code false} if negative
     */
    public static boolean isValidSquareRootOperand(double number) {
        return number >= 0.0;
    }

    /**
     * Validates whether the user's continuation response is Y or N (case-insensitive).
     *
     * @param response the user's response string
     * @return {@code true} if the response is Y or N; {@code false} otherwise
     */
    public static boolean isValidContinueResponse(String response) {
        if (response == null || response.isBlank()) {
            return false;
        }
        String normalized = response.trim().toUpperCase();
        return normalized.equals("Y") || normalized.equals("N");
    }

    /**
     * Determines whether the user wants to continue based on a validated Y/N response.
     *
     * @param response the user's response (expected to be Y or N)
     * @return {@code true} if the response is Y; {@code false} if N
     */
    public static boolean shouldContinue(String response) {
        return response.trim().toUpperCase().equals("Y");
    }
}
