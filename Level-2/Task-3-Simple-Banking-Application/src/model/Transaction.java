package model;

import java.time.LocalDateTime;
import java.util.Locale;

public class Transaction {


    public enum TransactionType {

        DEPOSIT,
        WITHDRAWAL,
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

    public String getTransactionId() {
        return transactionId;
    }


    public TransactionType getType() {
        return type;
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public String getRelatedAccountNumber() {
        return relatedAccountNumber;
    }


    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public String getDescription() {
        return description;
    }


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
