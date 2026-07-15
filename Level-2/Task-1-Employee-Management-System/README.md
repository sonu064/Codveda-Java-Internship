# Task 1 (Level 2) — Employee Management System

## Project Overview

A professional Java console application that manages employee records with full **CRUD** (Create, Read, Update, Delete) operations. Built using Core Java, Object-Oriented Programming, and the Collections Framework as part of the **Java Development Internship at Codveda Technologies**.

The system separates concerns across model, service, utility, and exception layers — following SOLID principles and clean architecture suitable for a production internship portfolio.

## Features

### CRUD Operations
- **Add Employee** — auto-generated ID (`EMP001`, `EMP002`, …), full field validation, duplicate email/phone prevention
- **View Employees** — formatted table display with total employee count
- **Search Employee** — by Employee ID, Name, or Department
- **Update Employee** — update name, department, designation, salary, email, phone (blank to keep current)
- **Delete Employee** — delete by ID with Y/N confirmation

### Statistics
- Total employees
- Highest, lowest, and average salary
- Department-wise employee count

### Validation & Error Handling
- Invalid integer input (`InputMismatchException`)
- Negative salary rejection
- Email format validation
- 10-digit phone number validation
- Empty name prevention
- Duplicate email and phone detection
- Custom `EmployeeNotFoundException`

### UI
- Professional welcome banner and menu
- ANSI console colors (when terminal supports)
- Formatted table output
- Clear success/error messages

## Technologies Used

- Java 21 (Java 17+ compatible)
- Core Java only (no external frameworks)
- `ArrayList` (Collections Framework)
- `java.util.Scanner` for console I/O
- IntelliJ IDEA / VS Code

## Folder Structure

```
Level-2/Task-1-Employee-Management-System/
├── src/
│   ├── Main.java
│   ├── model/
│   │   └── Employee.java
│   ├── service/
│   │   └── EmployeeService.java
│   ├── util/
│   │   ├── InputValidator.java
│   │   └── IdGenerator.java
│   └── exception/
│       └── EmployeeNotFoundException.java
├── screenshots/
├── README.md
└── .gitignore
```

## How to Run

From the `Level-2/Task-1-Employee-Management-System` directory:

**Windows (PowerShell / CMD):**

```bash
javac -d out src/model/Employee.java src/exception/EmployeeNotFoundException.java src/util/IdGenerator.java src/util/InputValidator.java src/service/EmployeeService.java src/Main.java
java -cp out Main
```

**Linux / macOS:**

```bash
javac -d out src/model/Employee.java src/exception/EmployeeNotFoundException.java src/util/IdGenerator.java src/util/InputValidator.java src/service/EmployeeService.java src/Main.java
java -cp out Main
```

Or open in IntelliJ IDEA, mark `src` as Sources Root, and run `Main.java`.

## Sample Output

```
=========================================
     EMPLOYEE MANAGEMENT SYSTEM
=========================================

  1. Add Employee
  2. View Employees
  ...
  7. Exit

Choose Option: 1

--- Add Employee ---

First Name: Sonu
Last Name: Singh
Age (18-65): 22
Gender (Male/Female/Other): Male
Department: Engineering
Designation: Java Intern
Salary: 25000
Email: sonu@codveda.com
Phone Number (10 digits): 9876543210

Employee Added Successfully! ID: EMP001

--- View Employees ---

----------------------------------------------------------------------------
ID       | First Name   | Last Name    | Age  | Gender   | Department     | ...
----------------------------------------------------------------------------
EMP001   | Sonu         | Singh        | 22   | Male     | Engineering    | ...
----------------------------------------------------------------------------

Total Employees: 1
```

## Screenshots Section

Add screenshots of the application running in your terminal:

| Screenshot | Description |
|------------|-------------|
| `screenshots/menu.png` | Main menu and welcome banner |
| `screenshots/add-employee.png` | Adding a new employee |
| `screenshots/view-employees.png` | Employee table view |
| `screenshots/statistics.png` | Workforce statistics |

## Future Improvements

- Persist data to file or database (JDBC / MySQL)
- REST API layer with Spring Boot
- Unit tests with JUnit 5 and Mockito
- Role-based access control (Admin / HR / Viewer)
- Export employee data to CSV/PDF
- Pagination for large employee lists
- Maven/Gradle build automation

## Author

**Sonu Singh** — Java Development Intern, Codveda Technologies
