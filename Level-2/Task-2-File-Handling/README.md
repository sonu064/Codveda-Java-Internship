# Task 2 (Level 2) — File Handling

## Project Overview

A professional Java console application that reads data from a text file, analyzes its content, and writes a statistical report to an output file. Built with Core Java file I/O APIs and Object-Oriented Programming principles as part of the **Java Development Internship at Codveda Technologies**.

The project demonstrates `BufferedReader`, `FileReader`, `BufferedWriter`, and `FileWriter` with proper exception handling and clean separation between UI, service, model, and utility layers.

## Features

- **Read File** — load text from `input/input.txt` using `BufferedReader`
- **Process File** — compute line, word, character, vowel, consonant, and digit counts
- **View Statistics** — display formatted results on the console
- **Export Report** — write results to `output/output.txt` using `BufferedWriter`
- **Exception Handling** — `FileNotFoundException`, `IOException`, empty file, invalid paths
- **Professional UI** — menu-driven console with ANSI colors

## Technologies Used

- Java 21 (Java 17+ compatible)
- Core Java File I/O (`BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter`)
- Collections Framework (`ArrayList`)
- IntelliJ IDEA / VS Code

## Folder Structure

```
Level-2/Task-2-File-Handling/
├── input/
│   └── input.txt
├── output/
│   └── output.txt
├── src/
│   ├── Main.java
│   ├── service/
│   │   └── FileProcessor.java
│   ├── model/
│   │   └── FileStatistics.java
│   ├── util/
│   │   └── FileValidator.java
│   └── exception/
│       └── FileProcessingException.java
├── screenshots/
├── README.md
└── .gitignore
```

## Input File Format

Plain text file (`.txt`) with one or more lines. Sample content is provided in `input/input.txt`.

```
Java Development Internship at Codveda Technologies

File Handling is an essential skill for every Java developer.
...
```

## Output File Format

Generated report in `output/output.txt`:

```
=========================================
FILE STATISTICS
=========================================

Source File        : input.txt
Total Lines        : 20
Total Words        : 185
Characters         : 1080
...

Report Generated Successfully.
Output saved to:
output/output.txt
```

## How to Run

From the `Level-2/Task-2-File-Handling` directory:

```bash
javac -d out src/model/FileStatistics.java src/exception/FileProcessingException.java src/util/FileValidator.java src/service/FileProcessor.java src/Main.java
java -cp out Main
```

**Menu workflow:** `1` Read File → `2` Process File → `3` View Statistics → `4` Export Report → `5` Exit

Or open in IntelliJ IDEA, mark `src` as Sources Root, set working directory to the task folder, and run `Main.java`.

## Sample Input

See `input/input.txt` — includes paragraphs, contact info, digits, and empty lines for realistic analysis.

## Sample Output

```
Reading File...

File Loaded Successfully.

Processing File...

File Processed Successfully.

=========================================
FILE STATISTICS
=========================================

Total Lines        : 22
Total Words        : 92
Characters         : 642
Empty Lines        : 7
Vowels             : 204
Consonants         : 330
Digits             : 14

Exporting Report...

Report Generated Successfully.
Output saved to:
output/output.txt
```

## Screenshots

| Screenshot | Description |
|------------|-------------|
| `screenshots/menu.png` | Main menu and welcome banner |
| `screenshots/statistics.png` | File statistics display |
| `screenshots/output-file.png` | Generated output.txt content |

## Future Improvements

- Support custom input/output file paths from the menu
- Add file encryption/decryption module
- Process multiple files in batch mode
- Generate PDF/HTML reports
- Unit tests with JUnit 5 and temporary file fixtures
- Maven/Gradle build automation

## Author

**Sonu Singh** — Java Development Intern, Codveda Technologies
