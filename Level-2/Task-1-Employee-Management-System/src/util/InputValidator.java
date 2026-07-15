package util;

public final class InputValidator {

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 7;

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 65;

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


    public static boolean isValidAge(int age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }

    public static boolean isValidSalary(double salary) {
        return salary >= 0;
    }


    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }


    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_PATTERN);
    }


    public static boolean isValidGender(String gender) {
        if (gender == null || gender.isBlank()) {
            return false;
        }
        String normalized = gender.trim().toUpperCase();
        return normalized.equals("MALE") || normalized.equals("FEMALE") || normalized.equals("OTHER")
                || normalized.equals("M") || normalized.equals("F") || normalized.equals("O");
    }


    public static String normalizeGender(String gender) {
        String normalized = gender.trim().toUpperCase();
        return switch (normalized) {
            case "M", "MALE" -> "Male";
            case "F", "FEMALE" -> "Female";
            default -> "Other";
        };
    }

    public static boolean isValidDepartment(String department) {
        return department != null && !department.isBlank();
    }

    
    public static boolean isValidDesignation(String designation) {
        return designation != null && !designation.isBlank();
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


    public static int getMinAge() {
        return MIN_AGE;
    }

    public static int getMaxAge() {
        return MAX_AGE;
    }


    public static int getPhoneNumberLength() {
        return PHONE_NUMBER_LENGTH;
    }
}
