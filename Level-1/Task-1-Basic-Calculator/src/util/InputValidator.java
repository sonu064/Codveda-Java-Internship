package util;

public final class InputValidator {

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 9;
    private InputValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    public static boolean isValidMenuOption(int option) {
        return option >= MIN_MENU_OPTION && option <= MAX_MENU_OPTION;
    }

    public static boolean isValidDivisor(double divisor) {
        return divisor != 0.0;
    }

    public static boolean isValidSquareRootOperand(double number) {
        return number >= 0.0;
    }

    public static boolean isValidContinueResponse(String response) {
        if (response == null || response.isBlank()) {
            return false;
        }
        String normalized = response.trim().toUpperCase();
        return normalized.equals("Y") || normalized.equals("N");
    }

    public static boolean shouldContinue(String response) {
        return response.trim().toUpperCase().equals("Y");
    }
}
