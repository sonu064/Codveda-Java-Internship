# Task 3 (Level 2) — Simple Banking Application

## Project Overview

A professional Java console application that simulates core banking operations — account creation, deposits, withdrawals, transfers, balance inquiries, and transaction history. Built with Core Java, OOP principles, custom exceptions, and the Collections Framework as part of the **Java Development Internship at Codveda Technologies**.

## Features

- **Create Account** — auto-generated account number (`ACC1001`, …), duplicate email prevention
- **Deposit Money** — credit funds and record transaction
- **Withdraw Money** — debit with `InsufficientBalanceException` when balance is low
- **Transfer Money** — move funds between accounts with dual transaction records
- **Check Balance** — view full account details and current balance
- **View All Accounts** — formatted table of all accounts
- **Transaction History** — all transactions or filtered by account
- **Delete Account** — remove account with confirmation (warns if balance remains)
- **Input Validation** — email, phone, amounts, names, `InputMismatchException` handling
- **Professional UI** — ANSI colors, welcome banner, formatted output

## Technologies Used

- Java 21 (Java 17+ compatible)
- Core Java (Collections Framework, `LocalDate`, `LocalDateTime`)
- Custom Exceptions
- IntelliJ IDEA / VS Code

## Folder Structure

```
Level-2/Task-3-Simple-Banking-Application/
├── src/
│   ├── Main.java
│   ├── model/
│   │   ├── BankAccount.java
│   │   ├── AccountType.java
│   │   └── Transaction.java
│   ├── service/
│   │   └── BankingService.java
│   ├── util/
│   │   ├── InputValidator.java
│   │   └── AccountNumberGenerator.java
│   └── exception/
│       ├── InsufficientBalanceException.java
│       ├── InvalidAmountException.java
│       └── AccountNotFoundException.java
├── screenshots/
├── README.md
└── .gitignore
```

## How to Run

From the `Level-2/Task-3-Simple-Banking-Application` directory:

```bash
javac -d out src/model/AccountType.java src/model/BankAccount.java src/model/Transaction.java src/exception/InsufficientBalanceException.java src/exception/InvalidAmountException.java src/exception/AccountNotFoundException.java src/util/AccountNumberGenerator.java src/util/InputValidator.java src/service/BankingService.java src/Main.java
java -cp out Main
```

Or open in IntelliJ IDEA, mark `src` as Sources Root, and run `Main.java`.

## Sample Output

```
=========================================
     BANKING MANAGEMENT SYSTEM
=========================================

  1. Create Account
  2. Deposit Money
  ...
  9. Exit

Choose Option: 1

--- Create Bank Account ---

Account Holder Name: Sonu Singh
Email: sonu@codveda.com
Phone Number (10 digits): 9876543210
Account Type (Savings/Current): Savings
Initial Balance: 10000

Account Created Successfully
Account Number : ACC1001
Current Balance: ₹10000.00

Choose Option: 2

--- Deposit Money ---

Account Number: ACC1001
Deposit Amount: 15000

Deposit Successful
Current Balance : ₹25000.00

Choose Option: 3

--- Withdraw Money ---

Account Number: ACC1001
Withdrawal Amount: 7000

Withdrawal Successful
Remaining Balance : ₹18000.00

Choose Option: 4

--- Transfer Money ---

From Account Number: ACC1001
To Account Number: ACC1002
Transfer Amount: 5000

Transfer Successful
```

## Screenshots

| Screenshot | Description |
|------------|-------------|
| `screenshots/menu.png` | Main menu and welcome banner |
| `screenshots/create-account.png` | Account creation flow |
| `screenshots/transaction-history.png` | Transaction history display |

## Future Improvements

- Persist accounts and transactions to file or database (JDBC)
- REST API with Spring Boot
- Unit tests with JUnit 5 and Mockito
- Interest calculation for savings accounts
- PIN/authentication for account access
- Export transaction statements to PDF/CSV
- Maven/Gradle build automation

## Author

**Sonu Singh** — Java Development Intern, Codveda Technologies
