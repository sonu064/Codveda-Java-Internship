package model;

/**
 * Represents the type of bank account offered by the system.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public enum AccountType {
    /** Savings account type. */
    SAVINGS("Savings"),
    /** Current account type. */
    CURRENT("Current");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human-readable account type label.
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Resolves an account type from user input (case-insensitive).
     *
     * @param value the user input
     * @return matching {@link AccountType}
     * @throws IllegalArgumentException if the value is invalid
     */
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
