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


public class BankingService {

    private static final String CURRENCY_SYMBOL = "\u20B9";

    private final List<BankAccount> accounts;
    private final List<Transaction> transactions;
    private int transactionCounter;


    public BankingService() {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.transactionCounter = 1;
    }

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

    public BankAccount deposit(String accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException {
        validateAmount(amount);
        BankAccount account = findAccount(accountNumber);
        account.credit(amount);
        recordTransaction(TransactionType.DEPOSIT, accountNumber, null, amount,
                account.getBalance(), "Deposit of " + formatCurrency(amount));
        return account;
    }

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


    public BankAccount findAccount(String accountNumber) throws AccountNotFoundException {
        return findAccountOptional(accountNumber)
                .orElseThrow(() -> AccountNotFoundException.forAccountNumber(accountNumber));
    }

    public List<BankAccount> getAllAccounts() {
        return Collections.unmodifiableList(accounts);
    }


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


    public BankAccount deleteAccount(String accountNumber) throws AccountNotFoundException {
        BankAccount account = findAccount(accountNumber);
        accounts.remove(account);
        return account;
    }

    public boolean hasAccounts() {
        return !accounts.isEmpty();
    }


    public int getAccountCount() {
        return accounts.size();
    }


    public static String formatCurrency(double amount) {
        return CURRENCY_SYMBOL + String.format("%.2f", amount);
    }

    private void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw InvalidAmountException.nonPositive(amount);
        }
    }


    private void recordTransaction(TransactionType type, String accountNumber,
                                 String relatedAccountNumber, double amount,
                                 double balanceAfter, String description) {
        String transactionId = "TXN" + transactionCounter++;
        Transaction transaction = new Transaction(
                transactionId, type, accountNumber, relatedAccountNumber,
                amount, balanceAfter, LocalDateTime.now(), description);
        transactions.add(transaction);
    }


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
