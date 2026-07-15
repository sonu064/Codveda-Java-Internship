import factorial.FactorialCalculator;
import util.InputValidator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class Main {

    private static final String APP_TITLE = "FACTORIAL CALCULATOR";
    private static final String BORDER = "=========================================";

    private static final boolean ANSI_SUPPORTED = System.console() != null;

    private static final String RESET = ANSI_SUPPORTED ? "\u001B[0m" : "";
    private static final String BOLD = ANSI_SUPPORTED ? "\u001B[1m" : "";
    private static final String CYAN = ANSI_SUPPORTED ? "\u001B[36m" : "";
    private static final String GREEN = ANSI_SUPPORTED ? "\u001B[32m" : "";
    private static final String YELLOW = ANSI_SUPPORTED ? "\u001B[33m" : "";
    private static final String RED = ANSI_SUPPORTED ? "\u001B[31m" : "";
    private static final String MAGENTA = ANSI_SUPPORTED ? "\u001B[35m" : "";

    private final FactorialCalculator factorialCalculator;
    private final List<String> calculationHistory;
    private final Scanner scanner;

    public Main() {
        this.factorialCalculator = new FactorialCalculator();
        this.calculationHistory = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }


    public static void main(String[] args) {
        Main application = new Main();
        application.run();
    }
    public void run() {
        displayWelcomeBanner();
        boolean running = true;

        while (running) {
            int number = readNonNegativeInteger();
            calculateAndDisplay(number);
            displayHistory();
            running = promptContinue();
        }

        displayGoodbyeMessage();
        scanner.close();
    }

    private void displayWelcomeBanner() {
        System.out.println();
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize(centerText(APP_TITLE, BORDER.length()), BOLD + CYAN));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  Recursive Factorial Calculator", GREEN));
        System.out.println(colorize("  Codveda Technologies — Java Internship", GREEN));
        System.out.println();
    }

    private int readNonNegativeInteger() {
        while (true) {
            try {
                System.out.print(colorize("Enter a non-negative integer: ", YELLOW));
                int number = scanner.nextInt();
                scanner.nextLine();

                if (!InputValidator.isNonNegative(number)) {
                    printError("Factorial is not defined for negative numbers.");
                    continue;
                }

                return number;
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private void calculateAndDisplay(int number) {
        System.out.println();
        System.out.println(colorize("Calculating...", MAGENTA));
        System.out.println();

        try {
            BigInteger result = factorialCalculator.calculateFactorial(number);
            displayRecursionSteps();
            displayResult(number, result);
            recordHistory(number, result);
        } catch (IllegalArgumentException exception) {
            printError(exception.getMessage());
        }
    }


    private void displayRecursionSteps() {
        if (!factorialCalculator.hasRecursionSteps()) {
            return;
        }

        System.out.println(colorize("--- Recursion Steps ---", BOLD));
        for (String step : factorialCalculator.getRecursionSteps()) {
            System.out.println("  " + step);
        }
        System.out.println();
    }


    private void displayResult(int number, BigInteger result) {
        System.out.println(colorize(String.format(Locale.US, "%d! = %s", number, result), BOLD + GREEN));
        System.out.println(colorize(String.format(Locale.US, "Factorial of %d = %s", number, result), GREEN));
        System.out.println();
    }


    private void recordHistory(int number, BigInteger result) {
        calculationHistory.add(String.format(Locale.US, "%d! = %s", number, result));
    }


    private void displayHistory() {
        if (calculationHistory.isEmpty()) {
            return;
        }

        System.out.println(colorize("--- Calculation History (This Session) ---", BOLD));
        for (int index = 0; index < calculationHistory.size(); index++) {
            System.out.printf(Locale.US, "  %d. %s%n", index + 1, calculationHistory.get(index));
        }
        System.out.println();
    }


    private boolean promptContinue() {
        while (true) {
            System.out.print(colorize("Would you like to calculate another factorial? (Y/N): ", YELLOW));
            String response = scanner.nextLine().trim();

            if (!InputValidator.isValidContinueResponse(response)) {
                printError("Invalid response. Please enter Y or N.");
                continue;
            }

            if (!InputValidator.shouldContinue(response)) {
                return false;
            }

            System.out.println();
            return true;
        }
    }

    private void displayGoodbyeMessage() {
        System.out.println();
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  Thank you for using Factorial Calculator!", GREEN));
        System.out.println(colorize("  Goodbye! Keep learning and coding.", GREEN));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
    }

    private void printError(String message) {
        System.out.println(colorize("Error: " + message, RED));
    }


    private void clearInvalidInput() {
        scanner.nextLine();
    }


    private String colorize(String text, String color) {
        return color + text + RESET;
    }


    private String centerText(String text, int width) {
        int padding = Math.max(0, (width - text.length()) / 2);
        return " ".repeat(padding) + text;
    }
}
