# Task 1 — Basic Calculator

## Objective

Build a professional Java console application that performs arithmetic operations while following Object-Oriented Programming principles and clean coding standards. Part of the **Java Development Internship at Codveda Technologies**.

## Features

- 8 arithmetic operations: Addition, Subtraction, Multiplication, Division, Modulus, Power, Square Root, Percentage
- Input validation for menu options, division by zero, and square root of negative numbers
- Exception handling for `InputMismatchException` and `ArithmeticException`
- Session-based calculation history
- Professional console UI with ANSI colors
- Continue option (Y/N) and goodbye message

## Technologies Used

- Java 21 (Java 17+ compatible)
- IntelliJ IDEA / VS Code
- Standard Java Libraries

## Folder Structure

```
Task-1-Basic-Calculator/
├── src/
│   ├── Main.java
│   ├── calculator/
│   │   └── Calculator.java
│   └── util/
│       └── InputValidator.java
├── README.md
└── .gitignore
```

## How to Run

From the `Task-1-Basic-Calculator` directory:

```bash
javac -d out src/calculator/Calculator.java src/util/InputValidator.java src/Main.java
java -cp out Main
```

Or open in IntelliJ IDEA, mark `src` as Sources Root, and run `Main.java`.

## Sample Output

```
==================================
      BASIC CALCULATOR
==================================

  1. Addition
  2. Subtraction
  ...
  9. Exit

Choose Option: 1

--- Addition ---
Enter first number: 25
Enter second number: 17

Result: 42
```

## Future Improvements

- Unit tests with JUnit 5
- Maven/Gradle build automation
- Persistent calculation history
- Scientific operations and GUI

## Author

**Sonu Singh** — Java Development Intern, Codveda Technologies
