package util;

public final class InputValidator {


    private InputValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }


    public static boolean isNonNegative(int number) {
        return number >= 0;
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
