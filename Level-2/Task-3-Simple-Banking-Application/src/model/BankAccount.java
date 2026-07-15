package model;

import java.time.LocalDate;
import java.util.Objects;


public class BankAccount {

    private String accountNumber;
    private String accountHolderName;
    private String email;
    private String phoneNumber;
    private AccountType accountType;
    private double balance;
    private LocalDate createdDate;


    public BankAccount() {

    }


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

    public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public String getAccountHolderName() {
        return accountHolderName;
    }


    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }

 
    public LocalDate getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }


    public void credit(double amount) {
        this.balance += amount;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }


    @Override
    public String toString() {
        return String.format("Account: %s | Holder: %s | Type: %s | Balance: %.2f | Email: %s",
                accountNumber, accountHolderName, accountType.getDisplayName(), balance, email);
    }


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


    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
