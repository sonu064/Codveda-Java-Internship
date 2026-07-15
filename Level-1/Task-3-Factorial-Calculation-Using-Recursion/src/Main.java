import factorial.FactorialCalculator;
import util.InputValidator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Entry point for the Factorial Calculator console application.
 * <p>
 * Handles user interface, input/output, exception handling, and session history.
 * Business logic is delegated to {@link FactorialCalculator}; validation to {@link InputValidator}.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
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

    /**
     * Constructs the application with initialized dependencies.
     */
    public Main() {
        this.factorialCalculator = new FactorialCalculator();
        this.calculationHistory = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Main application = new Main();
        application.run();
    }

    /**
     * Starts the factorial calculator application loop.
     */
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

    /**
     * Displays the welcome banner.
     */
    private void displayWelcomeBanner() {
        System.out.println();
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize(centerText(APP_TITLE, BORDER.length()), BOLD + CYAN));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  Recursive Factorial Calculator", GREEN));
        System.out.println(colorize("  Codveda Technologies — Java Internship", GREEN));
        System.out.println();
    }

    /**
     * Reads a non-negative integer from the user with validation and exception handling.
     *
     * @return a valid non-negative integer
     */
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

    /**
     * Calculates factorial and displays the result with recursion steps.
     *
     * @param number the non-negative integer
     */
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

    /**
     * Displays the recursive calculation steps from the last computation.
     */
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

    /**
     * Displays the formatted factorial result.
     *
     * @param number the input number
     * @param result the computed factorial
     */
    private void displayResult(int number, BigInteger result) {
        System.out.println(colorize(String.format(Locale.US, "%d! = %s", number, result), BOLD + GREEN));
        System.out.println(colorize(String.format(Locale.US, "Factorial of %d = %s", number, result), GREEN));
        System.out.println();
    }

    /**
     * Records the calculation in session history.
     *
     * @param number the input number
     * @param result the computed factorial
     */
    private void recordHistory(int number, BigInteger result) {
        calculationHistory.add(String.format(Locale.US, "%d! = %s", number, result));
    }

    /**
     * Displays the calculation history for the current session.
     */
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

    /**
     * Prompts whether the user wants to calculate another factorial.
     *
     * @return {@code true} to continue; {@code false} to exit
     */
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

    /**
     * Displays the goodbye message when the application exits.
     */
    private void displayGoodbyeMessage() {
        System.out.println();
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  Thank you for using Factorial Calculator!", GREEN));
        System.out.println(colorize("  Goodbye! Keep learning and coding.", GREEN));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
    }

    /**
     * Prints an error message to the console.
     *
     * @param message the error message
     */
    private void printError(String message) {
        System.out.println(colorize("Error: " + message, RED));
    }

    /**
     * Clears invalid input from the scanner buffer after an input mismatch.
     */
    private void clearInvalidInput() {
        scanner.nextLine();
    }

    /**
     * Applies ANSI color codes when the terminal supports them.
     *
     * @param text  the text to colorize
     * @param color the ANSI color prefix
     * @return colorized or plain text
     */
    private String colorize(String text, String color) {
        return color + text + RESET;
    }

    /**
     * Centers text within a given width.
     *
     * @param text  the text to center
     * @param width the total line width
     * @return centered text
     */
    private String centerText(String text, int width) {
        int padding = Math.max(0, (width - text.length()) / 2);
        return " ".repeat(padding) + text;
    }
}
