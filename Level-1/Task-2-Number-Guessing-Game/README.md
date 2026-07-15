# Task 2 — Number Guessing Game

## Project Overview

A professional Java console application where the computer generates a random number and the player tries to guess it within a limited number of attempts. Built as part of the **Java Development Internship at Codveda Technologies**.

## Features

- Random number generation using `java.util.Random` (regenerated every round)
- Three difficulty levels: Easy (1–50), Medium (1–100), Hard (1–500)
- Hints: Too High, Too Low, Correct Guess
- Maximum 10 attempts with remaining attempt countdown
- Input validation and `InputMismatchException` handling
- Session statistics: games played, won, lost, best score
- Play again option (Y/N) with professional console UI and ANSI colors

## Technologies Used

- Java 21 (Java 17+ compatible)
- IntelliJ IDEA / VS Code
- Standard Java Libraries

## Folder Structure

```
Task-2-Number-Guessing-Game/
├── src/
│   ├── Main.java
│   ├── game/
│   │   └── NumberGuessingGame.java
│   ├── model/
│   │   └── GameStatistics.java
│   └── util/
│       └── InputValidator.java
├── README.md
└── .gitignore
```

## How to Run

From the `Task-2-Number-Guessing-Game` directory:

```bash
javac -d out src/model/GameStatistics.java src/game/NumberGuessingGame.java src/util/InputValidator.java src/Main.java
java -cp out Main
```

Or open in IntelliJ IDEA, mark `src` as Sources Root, and run `Main.java`.

## Sample Output

```
========================================
        NUMBER GUESSING GAME
========================================

Difficulty:
  1. Easy (1-50)
  2. Medium (1-100)
  3. Hard (1-500)

Choose Difficulty: 2

Guess the number: 50

Wrong!
Hint: Too Low
Remaining Attempts: 9

Congratulations! You guessed the number correctly!
You guessed the number in 4 attempts.

==============================
Game Statistics
==============================
Games Played : 1
Games Won    : 1
Games Lost   : 0
Best Score   : 4 Attempts
==============================
```

## Future Improvements

- Unit tests with JUnit 5
- Persistent leaderboard
- Timed mode and multiplayer support
- JavaFX/Swing graphical interface
- Maven/Gradle build automation

## Author

**Sonu Singh** — Java Development Intern, Codveda Technologies
