# Task 1 (Level 3) — Library Management System with JDBC

## Project Overview

A professional Java console application that manages a library using **JDBC** and **MySQL**. Supports full book and user management, borrow/return operations with database transactions, and borrowing history — built with the **DAO pattern**, custom exceptions, and clean architecture as part of the **Java Development Internship at Codveda Technologies**.

## Features

- **Book Management** — Add, view, search, update, delete books
- **User Management** — Register and view library members
- **Borrow / Return** — JDBC transactions with commit/rollback
- **Borrow History** — All transactions or filtered by user
- **Validation** — Duplicate ISBN/email, insufficient copies, invalid input
- **Custom Exceptions** — `BookNotFoundException`, `UserNotFoundException`, `DatabaseException`
- **Professional Console UI** — ANSI colors, formatted tables

## Technologies Used

- Java 21
- JDBC (`PreparedStatement`, `ResultSet`, transactions)
- MySQL 8
- Maven (`mysql-connector-j`, `slf4j-simple`)
- DAO Pattern, OOP, SOLID Principles

## Folder Structure

```
Level-3/Task-1-Library-Management-System-JDBC/
├── database/
│   ├── library_schema.sql
│   ├── sample_data.sql
│   └── README.md
├── src/
│   └── main/
│       └── java/
│           ├── com/codveda/library/Main.java
│           ├── config/DBConnection.java
│           ├── model/Book.java, User.java, BorrowTransaction.java
│           ├── dao/BookDAO.java, UserDAO.java, BorrowDAO.java
│           ├── service/LibraryService.java
│           ├── util/InputValidator.java, IdGenerator.java
│           └── exception/*.java
├── screenshots/
├── pom.xml
├── README.md
└── .gitignore
```

## Database Schema

### books
| Column | Type | Description |
|--------|------|-------------|
| book_id | INT PK AUTO_INCREMENT | Unique book ID |
| title | VARCHAR(200) | Book title |
| author | VARCHAR(150) | Author name |
| category | VARCHAR(100) | Category |
| isbn | VARCHAR(20) UNIQUE | ISBN number |
| quantity | INT | Total copies |
| available_quantity | INT | Available copies |

### users
| Column | Type | Description |
|--------|------|-------------|
| user_id | INT PK AUTO_INCREMENT | Unique user ID |
| full_name | VARCHAR(150) | Full name |
| email | VARCHAR(150) UNIQUE | Email address |
| phone | VARCHAR(15) | Phone number |

### borrow_transactions
| Column | Type | Description |
|--------|------|-------------|
| transaction_id | INT PK AUTO_INCREMENT | Transaction ID |
| user_id | INT FK | Borrowing user |
| book_id | INT FK | Borrowed book |
| borrow_date | DATE | Borrow date |
| return_date | DATE | Return date (nullable) |
| status | VARCHAR(20) | BORROWED / RETURNED |

## How to Configure MySQL

1. Install and start MySQL 8.
2. Run the database scripts:

```bash
mysql -u root -p < database/library_schema.sql
mysql -u root -p < database/sample_data.sql
```

3. Update credentials in `src/main/java/config/DBConnection.java`:

```java
private static final String DB_URL =
        "jdbc:mysql://127.0.0.1:3306/library_management"
                + "?allowPublicKeyRetrieval=true"
                + "&useSSL=false"
                + "&serverTimezone=UTC";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

**Important:** After changing credentials, recompile before running:

```bash
mvn clean compile exec:java
```

`mvn exec:java` alone does **not** recompile, so an old `DBConnection.class` (for example with an empty password) can still be used.

## How to Run

### Using Maven (Recommended)

```bash
cd Level-3/Task-1-Library-Management-System-JDBC
mvn compile exec:java
```

### Using javac (manual)

Download `mysql-connector-j` JAR and compile with it on the classpath.

## Sample Output

```
=========================================
   LIBRARY MANAGEMENT SYSTEM
=========================================

Database Connected Successfully.

  1.  Add Book
  ...
  11. Exit

Choose Option: 1

--- Add Book ---

Title: Effective Java
Author: Joshua Bloch
Category: Programming
ISBN: 978-0134685991
Quantity: 5

Book Added Successfully
Book ID: 9 | Effective Java

Choose Option: 8

--- Borrow Book ---

User ID: 1
Book ID: 1

Book Borrowed Successfully
Transaction ID: 1

Choose Option: 9

--- Return Book ---

Transaction ID: 1

Book Returned Successfully
```

## Screenshots

| Screenshot | Description |
|------------|-------------|
| `screenshots/menu.png` | Main menu with database connection |
| `screenshots/books-table.png` | View all books table |
| `screenshots/borrow-history.png` | Borrow transaction history |

## Future Improvements

- REST API with Spring Boot
- Connection pooling (HikariCP)
- Admin authentication and role-based access
- Fine calculation for overdue books
- Unit tests with JUnit 5 and Testcontainers
- Docker Compose for MySQL setup

## Author

**Sonu Singh** — Java Development Intern, Codveda Technologies
