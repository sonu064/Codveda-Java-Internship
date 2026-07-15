package model;


public enum AccountType {

    SAVINGS("Savings"),
    CURRENT("Current");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


    public static AccountType fromString(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Account type cannot be empty.");
        }
        String normalized = value.trim().toUpperCase();
        return switch (normalized) {
            case "SAVINGS", "S" -> SAVINGS;
            case "CURRENT", "C" -> CURRENT;
            default -> throw new IllegalArgumentException("Invalid account type: " + value);
        };
    }
}
