package service;

import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import model.AccountType;
import model.BankAccount;
import model.Transaction;
import model.Transaction.TransactionType;
import util.AccountNumberGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for banking operations and account management.
 * <p>
 * Manages accounts and transactions in memory using {@link ArrayList}.
 * Contains no console I/O — single responsibility for business logic.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class BankingService {

    private static final String CURRENCY_SYMBOL = "\u20B9";

    private final List<BankAccount> accounts;
    private final List<Transaction> transactions;
    private int transactionCounter;

    /**
     * Creates a new banking service with empty repositories.
     */
    public BankingService() {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.transactionCounter = 1;
    }

    /**
     * Creates a new bank account with an auto-generated account number.
     *
     * @param accountHolderName name of the holder
     * @param email             email address
     * @param phoneNumber       phone number
     * @param accountType       savings or current
     * @param initialBalance    opening balance
     * @return the created account
     * @throws InvalidAmountException   if initial balance is negative
     * @throws IllegalArgumentException if email is duplicate
     */
    public BankAccount createAccount(String accountHolderName, String email, String phoneNumber,
                                     AccountType accountType, double initialBalance)
            throws InvalidAmountException {
        if (initialBalance < 0) {
            throw InvalidAmountException.nonPositive(initialBalance);
        }
        if (isEmailDuplicate(email)) {
            throw new IllegalArgumentException("Duplicate email address: " + email);
        }

        BankAccount account = new BankAccount(
                AccountNumberGenerator.generateAccountNumber(),
                accountHolderName,
                email,
                phoneNumber,
                accountType,
                initialBalance,
                LocalDate.now());

        accounts.add(account);

        if (initialBalance > 0) {
            recordTransaction(TransactionType.DEPOSIT, account.getAccountNumber(), null,
                    initialBalance, account.getBalance(),
                    "Initial deposit");
        }

        return account;
    }

    /**
     * Deposits money into the specified account.
     *
     * @param accountNumber target account
     * @param amount        deposit amount
     * @return updated account
     * @throws AccountNotFoundException if account not found
     * @throws InvalidAmountException   if amount is invalid
     */
    public BankAccount deposit(String accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException {
        validateAmount(amount);
        BankAccount account = findAccount(accountNumber);
        account.credit(amount);
        recordTransaction(TransactionType.DEPOSIT, accountNumber, null, amount,
                account.getBalance(), "Deposit of " + formatCurrency(amount));
        return account;
    }

    /**
     * Withdraws money from the specified account.
     *
     * @param accountNumber source account
     * @param amount        withdrawal amount
     * @return updated account
     * @throws AccountNotFoundException      if account not found
     * @throws InvalidAmountException        if amount is invalid
     * @throws InsufficientBalanceException  if balance is insufficient
     */
    public BankAccount withdraw(String accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientBalanceException {
        validateAmount(amount);
        BankAccount account = findAccount(accountNumber);

        if (account.getBalance() < amount) {
            throw InsufficientBalanceException.forAccount(
                    accountNumber, account.getBalance(), amount);
        }

        account.debit(amount);
        recordTransaction(TransactionType.WITHDRAWAL, accountNumber, null, amount,
                account.getBalance(), "Withdrawal of " + formatCurrency(amount));
        return account;
    }

    /**
     * Transfers money between two accounts.
     *
     * @param fromAccountNumber sender account
     * @param toAccountNumber   receiver account
     * @param amount            transfer amount
     * @throws AccountNotFoundException      if either account not found
     * @throws InvalidAmountException        if amount is invalid
     * @throws InsufficientBalanceException  if sender has insufficient balance
     * @throws IllegalArgumentException      if transferring to the same account
     */
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException,
            InsufficientBalanceException {
        validateAmount(amount);

        if (fromAccountNumber.equalsIgnoreCase(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        BankAccount sender = findAccount(fromAccountNumber);
        BankAccount receiver = findAccount(toAccountNumber);

        if (sender.getBalance() < amount) {
            throw InsufficientBalanceException.forAccount(
                    fromAccountNumber, sender.getBalance(), amount);
        }

        sender.debit(amount);
        receiver.credit(amount);

        String description = "Transfer of " + formatCurrency(amount)
                + " to " + toAccountNumber;

        recordTransaction(TransactionType.TRANSFER, fromAccountNumber, toAccountNumber,
                amount, sender.getBalance(), description);

        recordTransaction(TransactionType.TRANSFER, toAccountNumber, fromAccountNumber,
                amount, receiver.getBalance(),
                "Received " + formatCurrency(amount) + " from " + fromAccountNumber);
    }

    /**
     * Finds an account by account number.
     *
     * @param accountNumber the account number
     * @return the matching account
     * @throws AccountNotFoundException if not found
     */
    public BankAccount findAccount(String accountNumber) throws AccountNotFoundException {
        return findAccountOptional(accountNumber)
                .orElseThrow(() -> AccountNotFoundException.forAccountNumber(accountNumber));
    }

    /**
     * Returns all bank accounts.
     *
     * @return unmodifiable list of accounts
     */
    public List<BankAccount> getAllAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    /**
     * Returns all transactions, optionally filtered by account number.
     *
     * @param accountNumber account to filter by, or {@code null} for all
     * @return matching transactions
     */
    public List<Transaction> getTransactionHistory(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            return Collections.unmodifiableList(transactions);
        }

        String normalized = accountNumber.trim().toUpperCase();
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber().equalsIgnoreCase(normalized)
                    || (transaction.getRelatedAccountNumber() != null
                    && transaction.getRelatedAccountNumber().equalsIgnoreCase(normalized))) {
                filtered.add(transaction);
            }
        }
        return Collections.unmodifiableList(filtered);
    }

    /**
     * Deletes an account by account number.
     *
     * @param accountNumber the account to delete
     * @return the deleted account
     * @throws AccountNotFoundException if not found
     */
    public BankAccount deleteAccount(String accountNumber) throws AccountNotFoundException {
        BankAccount account = findAccount(accountNumber);
        accounts.remove(account);
        return account;
    }

    /**
     * Checks whether any accounts exist.
     *
     * @return {@code true} if accounts exist
     */
    public boolean hasAccounts() {
        return !accounts.isEmpty();
    }

    /**
     * Returns the total number of accounts.
     *
     * @return account count
     */
    public int getAccountCount() {
        return accounts.size();
    }

    /**
     * Formats an amount with the currency symbol.
     *
     * @param amount the amount
     * @return formatted currency string
     */
    public static String formatCurrency(double amount) {
        return CURRENCY_SYMBOL + String.format("%.2f", amount);
    }

    /**
     * Validates that an amount is positive.
     *
     * @param amount the amount to validate
     * @throws InvalidAmountException if amount is not positive
     */
    private void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw InvalidAmountException.nonPositive(amount);
        }
    }

    /**
     * Records a transaction in the history.
     *
     * @param type                 transaction type
     * @param accountNumber        primary account
     * @param relatedAccountNumber related account for transfers
     * @param amount               transaction amount
     * @param balanceAfter         balance after transaction
     * @param description          description
     */
    private void recordTransaction(TransactionType type, String accountNumber,
                                 String relatedAccountNumber, double amount,
                                 double balanceAfter, String description) {
        String transactionId = "TXN" + transactionCounter++;
        Transaction transaction = new Transaction(
                transactionId, type, accountNumber, relatedAccountNumber,
                amount, balanceAfter, LocalDateTime.now(), description);
        transactions.add(transaction);
    }

    /**
     * Finds an account optionally.
     *
     * @param accountNumber account number
     * @return optional account
     */
    private Optional<BankAccount> findAccountOptional(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            return Optional.empty();
        }
        String normalized = accountNumber.trim().toUpperCase();
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equalsIgnoreCase(normalized)) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks for duplicate email addresses.
     *
     * @param email email to check
     * @return {@code true} if duplicate exists
     */
    private boolean isEmailDuplicate(String email) {
        String normalized = email.trim().toLowerCase();
        for (BankAccount account : accounts) {
            if (account.getEmail().equalsIgnoreCase(normalized)) {
                return true;
            }
        }
        return false;
    }
}
