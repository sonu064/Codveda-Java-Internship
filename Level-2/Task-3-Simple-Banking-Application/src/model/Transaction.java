package model;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Represents a financial transaction in the banking system.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class Transaction {

    /**
     * Types of banking transactions supported by the system.
     */
    public enum TransactionType {
        /** Money deposited into an account. */
        DEPOSIT,
        /** Money withdrawn from an account. */
        WITHDRAWAL,
        /** Money transferred between accounts. */
        TRANSFER
    }

    private final String transactionId;
    private final TransactionType type;
    private final String accountNumber;
    private final String relatedAccountNumber;
    private final double amount;
    private final double balanceAfter;
    private final LocalDateTime timestamp;
    private final String description;

    /**
     * Creates a transaction record.
     *
     * @param transactionId        unique transaction identifier
     * @param type                 transaction type
     * @param accountNumber        primary account number
     * @param relatedAccountNumber related account (for transfers), or {@code null}
     * @param amount               transaction amount
     * @param balanceAfter         balance after the transaction
     * @param timestamp            when the transaction occurred
     * @param description          human-readable description
     */
    public Transaction(String transactionId, TransactionType type, String accountNumber,
                       String relatedAccountNumber, double amount, double balanceAfter,
                       LocalDateTime timestamp, String description) {
        this.transactionId = transactionId;
        this.type = type;
        this.accountNumber = accountNumber;
        this.relatedAccountNumber = relatedAccountNumber;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = timestamp;
        this.description = description;
    }

    /**
     * Returns the transaction ID.
     *
     * @return transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the transaction type.
     *
     * @return type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Returns the primary account number.
     *
     * @return account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Returns the related account number for transfers.
     *
     * @return related account number, or {@code null}
     */
    public String getRelatedAccountNumber() {
        return relatedAccountNumber;
    }

    /**
     * Returns the transaction amount.
     *
     * @return amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the balance after the transaction.
     *
     * @return balance after transaction
     */
    public double getBalanceAfter() {
        return balanceAfter;
    }

    /**
     * Returns the transaction timestamp.
     *
     * @return timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the transaction description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a formatted transaction summary for display.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        String related = relatedAccountNumber != null
                ? " | Related: " + relatedAccountNumber
                : "";
        return String.format(Locale.US,
                "[%s] %s | Account: %s%s | Amount: %.2f | Balance After: %.2f | %s",
                transactionId, type, accountNumber, related, amount, balanceAfter, description);
    }
}
