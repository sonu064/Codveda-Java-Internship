# Task 3 — Factorial Calculation Using Recursion

## Project Overview

A professional Java console application that calculates the factorial of a non-negative integer using **recursion**. Built with Object-Oriented Programming principles, clean architecture, and production-quality coding standards as part of the **Java Development Internship at Codveda Technologies**.

The application separates UI, validation, and business logic, handles edge cases gracefully, and displays recursive calculation steps for educational clarity.

## Features

- **Recursive Factorial** — pure recursive implementation in `FactorialCalculator`
- **Edge Case Handling** — factorial of 0, 1, and rejection of negative numbers
- **BigInteger Support** — avoids overflow for larger factorial results
- **Recursion Steps** — visual trace of each recursive call
- **Input Validation** — non-negative integers and Y/N continue responses
- **Exception Handling** — `InputMismatchException` handled without crashing
- **Session History** — tracks all calculations in the current session
- **Professional Console UI** — welcome banner, ANSI colors, formatted output
- **Multi-round Support** — calculate multiple factorials until the user exits

## Technologies Used

- Java 21 (Java 17+ compatible)
- IntelliJ IDEA / VS Code
- Standard Java Libraries (`java.math.BigInteger`, `java.util.Scanner`)

## Folder Structure

```
Task-3-Factorial-Calculation-Using-Recursion/
├── src/
│   ├── Main.java
│   ├── factorial/
│   │   └── FactorialCalculator.java
│   └── util/
│       └── InputValidator.java
├── README.md
└── .gitignore
```

## How to Run

From the `Task-3-Factorial-Calculation-Using-Recursion` directory:

**Windows (PowerShell / CMD):**

```bash
javac -d out src/factorial/FactorialCalculator.java src/util/InputValidator.java src/Main.java
java -cp out Main
```

**Linux / macOS:**

```bash
javac -d out src/factorial/FactorialCalculator.java src/util/InputValidator.java src/Main.java
java -cp out Main
```

Or open in IntelliJ IDEA, mark `src` as Sources Root, and run `Main.java`.

## Sample Input/Output

### Factorial of 5

```
Enter a non-negative integer: 5

Calculating...

--- Recursion Steps ---
  1! = 1  (base case)
  2! = 2 × 1! = 2
  3! = 3 × 2! = 6
  4! = 4 × 3! = 24
  5! = 5 × 4! = 120

5! = 120
Factorial of 5 = 120
```

### Factorial of 0

```
Enter a non-negative integer: 0

Calculating...

--- Recursion Steps ---
  0! = 1  (base case)

0! = 1
Factorial of 0 = 1
```

### Negative Number

```
Enter a non-negative integer: -5
Error: Factorial is not defined for negative numbers.
Enter a non-negative integer:
```

### Invalid Input

```
Enter a non-negative integer: abc
Error: Invalid input. Please enter a valid integer.
Enter a non-negative integer:
```

## Future Improvements

- Add unit tests with JUnit 5 for recursive logic and validation
- Add iterative factorial method for performance comparison
- Display execution time and recursion depth metrics
- Add memoization (dynamic programming) variant
- Maven/Gradle build automation
- GUI with step-by-step recursion visualization

## Author

**Sonu Singh** — Java Development Intern, Codveda Technologies
