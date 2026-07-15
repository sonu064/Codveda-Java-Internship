package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a bank account in the banking management system.
 * <p>
 * Encapsulates account holder details, balance, and metadata.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class BankAccount {

    private String accountNumber;
    private String accountHolderName;
    private String email;
    private String phoneNumber;
    private AccountType accountType;
    private double balance;
    private LocalDate createdDate;

    /**
     * Default constructor for object initialization via setters.
     */
    public BankAccount() {
        // Fields assigned via setters or parameterized constructor.
    }

    /**
     * Creates a bank account with all required fields.
     *
     * @param accountNumber      unique account number
     * @param accountHolderName  name of the account holder
     * @param email              email address
     * @param phoneNumber        contact phone number
     * @param accountType        type of account
     * @param balance            initial balance
     * @param createdDate        account creation date
     */
    public BankAccount(String accountNumber, String accountHolderName, String email,
                       String phoneNumber, AccountType accountType, double balance,
                       LocalDate createdDate) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.createdDate = createdDate;
    }

    /**
     * Returns the account number.
     *
     * @return account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the account number.
     *
     * @param accountNumber unique account number
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Returns the account holder name.
     *
     * @return account holder name
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * Sets the account holder name.
     *
     * @param accountHolderName name of the holder
     */
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    /**
     * Returns the email address.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number.
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber contact number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the account type.
     *
     * @return account type
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Sets the account type.
     *
     * @param accountType savings or current
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * Returns the current balance.
     *
     * @return balance amount
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account balance.
     *
     * @param balance current balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Returns the account creation date.
     *
     * @return created date
     */
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the account creation date.
     *
     * @param createdDate creation date
     */
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Credits the specified amount to the account balance.
     *
     * @param amount amount to credit
     */
    public void credit(double amount) {
        this.balance += amount;
    }

    /**
     * Debits the specified amount from the account balance.
     *
     * @param amount amount to debit
     */
    public void debit(double amount) {
        this.balance -= amount;
    }

    /**
     * Returns a formatted summary of the account.
     *
     * @return account summary string
     */
    @Override
    public String toString() {
        return String.format("Account: %s | Holder: %s | Type: %s | Balance: %.2f | Email: %s",
                accountNumber, accountHolderName, accountType.getDisplayName(), balance, email);
    }

    /**
     * Compares accounts by account number.
     *
     * @param object the reference object
     * @return {@code true} if account numbers match
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        BankAccount that = (BankAccount) object;
        return Objects.equals(accountNumber, that.accountNumber);
    }

    /**
     * Returns hash code based on account number.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
