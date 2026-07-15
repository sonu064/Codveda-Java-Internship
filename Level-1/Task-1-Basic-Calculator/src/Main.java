import calculator.Calculator;
import util.InputValidator;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Entry point for the Basic Calculator console application.
 * <p>
 * Handles user interface, input/output, exception handling, and session history.
 * Business logic is delegated to {@link Calculator}; validation to {@link InputValidator}.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class Main {

    private static final int MENU_ADDITION = 1;
    private static final int MENU_SUBTRACTION = 2;
    private static final int MENU_MULTIPLICATION = 3;
    private static final int MENU_DIVISION = 4;
    private static final int MENU_MODULUS = 5;
    private static final int MENU_POWER = 6;
    private static final int MENU_SQUARE_ROOT = 7;
    private static final int MENU_PERCENTAGE = 8;
    private static final int MENU_EXIT = 9;

    private static final String APP_TITLE = "BASIC CALCULATOR";
    private static final String BORDER = "==================================";

    private static final boolean ANSI_SUPPORTED = System.console() != null;

    private static final String RESET = ANSI_SUPPORTED ? "\u001B[0m" : "";
    private static final String BOLD = ANSI_SUPPORTED ? "\u001B[1m" : "";
    private static final String CYAN = ANSI_SUPPORTED ? "\u001B[36m" : "";
    private static final String GREEN = ANSI_SUPPORTED ? "\u001B[32m" : "";
    private static final String YELLOW = ANSI_SUPPORTED ? "\u001B[33m" : "";
    private static final String RED = ANSI_SUPPORTED ? "\u001B[31m" : "";

    private final Calculator calculator;
    private final List<String> calculationHistory;
    private final Scanner scanner;

    /**
     * Constructs the application with initialized dependencies.
     */
    public Main() {
        this.calculator = new Calculator();
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
     * Starts the calculator application loop.
     */
    public void run() {
        displayWelcomeBanner();
        boolean running = true;

        while (running) {
            displayMenu();
            int menuOption = readMenuOption();

            if (menuOption == MENU_EXIT) {
                running = false;
                continue;
            }

            processOperation(menuOption);
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
        System.out.println(colorize("  Professional Console Calculator", GREEN));
        System.out.println(colorize("  Codveda Technologies — Java Internship", GREEN));
        System.out.println();
    }

    /**
     * Displays the operation menu.
     */
    private void displayMenu() {
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("      " + APP_TITLE, BOLD));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
        System.out.println("  1. Addition");
        System.out.println("  2. Subtraction");
        System.out.println("  3. Multiplication");
        System.out.println("  4. Division");
        System.out.println("  5. Modulus");
        System.out.println("  6. Power");
        System.out.println("  7. Square Root");
        System.out.println("  8. Percentage");
        System.out.println("  9. Exit");
        System.out.println();
        System.out.print(colorize("Choose Option: ", YELLOW));
    }

    /**
     * Reads and validates a menu option from the user.
     *
     * @return a valid menu option between 1 and 9
     */
    private int readMenuOption() {
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                if (InputValidator.isValidMenuOption(option)) {
                    return option;
                }

                printError("Invalid option. Please choose a number between 1 and 9.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a numeric menu option.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            }
        }
    }

    /**
     * Dispatches the selected operation to the appropriate handler.
     *
     * @param menuOption the validated menu option
     */
    private void processOperation(int menuOption) {
        try {
            switch (menuOption) {
                case MENU_ADDITION -> performBinaryOperation("Addition", "+",
                        (a, b) -> calculator.add(a, b));
                case MENU_SUBTRACTION -> performBinaryOperation("Subtraction", "-",
                        (a, b) -> calculator.subtract(a, b));
                case MENU_MULTIPLICATION -> performBinaryOperation("Multiplication", "x",
                        (a, b) -> calculator.multiply(a, b));
                case MENU_DIVISION -> performDivisionOrModulus("Division", "/",
                        (a, b) -> calculator.divide(a, b));
                case MENU_MODULUS -> performDivisionOrModulus("Modulus", "%",
                        (a, b) -> calculator.modulus(a, b));
                case MENU_POWER -> performBinaryOperation("Power", "^",
                        (a, b) -> calculator.power(a, b));
                case MENU_SQUARE_ROOT -> performSquareRoot();
                case MENU_PERCENTAGE -> performPercentage();
                default -> printError("Unsupported operation selected.");
            }
        } catch (ArithmeticException exception) {
            printError(exception.getMessage());
        }
    }

    /**
     * Performs a binary arithmetic operation with two operands.
     *
     * @param operationName display name of the operation
     * @param symbol        mathematical symbol for history formatting
     * @param operation     the computation to execute
     */
    private void performBinaryOperation(String operationName, String symbol,
                                        BinaryOperation operation) {
        System.out.println();
        System.out.println(colorize("--- " + operationName + " ---", BOLD));

        double firstOperand = readDouble("Enter first number: ");
        double secondOperand = readDouble("Enter second number: ");

        double result = operation.apply(firstOperand, secondOperand);
        recordAndDisplayResult(
                formatBinaryHistory(firstOperand, symbol, secondOperand, result),
                result);
    }

    /**
     * Performs division or modulus with divisor validation.
     *
     * @param operationName display name of the operation
     * @param symbol        mathematical symbol for history formatting
     * @param operation     the computation to execute
     */
    private void performDivisionOrModulus(String operationName, String symbol,
                                          BinaryOperation operation) {
        System.out.println();
        System.out.println(colorize("--- " + operationName + " ---", BOLD));

        double dividend = readDouble("Enter first number: ");
        double divisor = readDouble("Enter second number: ");

        if (!InputValidator.isValidDivisor(divisor)) {
            printError("Divisor cannot be zero. Operation cancelled.");
            return;
        }

        double result = operation.apply(dividend, divisor);
        recordAndDisplayResult(
                formatBinaryHistory(dividend, symbol, divisor, result),
                result);
    }

    /**
     * Performs square root with operand validation.
     */
    private void performSquareRoot() {
        System.out.println();
        System.out.println(colorize("--- Square Root ---", BOLD));

        double number = readDouble("Enter number: ");

        if (!InputValidator.isValidSquareRootOperand(number)) {
            printError("Cannot calculate square root of a negative number.");
            return;
        }

        double result = calculator.squareRoot(number);
        recordAndDisplayResult(
                String.format(Locale.US, "sqrt(%s) = %s", formatNumber(number), formatNumber(result)),
                result);
    }

    /**
     * Performs percentage calculation.
     */
    private void performPercentage() {
        System.out.println();
        System.out.println(colorize("--- Percentage ---", BOLD));

        double value = readDouble("Enter value: ");
        double percent = readDouble("Enter percentage: ");

        double result = calculator.percentage(value, percent);
        recordAndDisplayResult(
                String.format(Locale.US, "%s%% of %s = %s",
                        formatNumber(percent), formatNumber(value), formatNumber(result)),
                result);
    }

    /**
     * Reads a valid double value from the user.
     *
     * @param prompt the input prompt message
     * @return the parsed double value
     */
    private double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(colorize(prompt, YELLOW));
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Records a calculation in history and prints the formatted result.
     *
     * @param historyEntry the history record string
     * @param result       the computed result
     */
    private void recordAndDisplayResult(String historyEntry, double result) {
        calculationHistory.add(historyEntry);
        System.out.println();
        System.out.println(colorize("Result: " + formatNumber(result), BOLD + GREEN));
        System.out.println();
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
     * Prompts the user whether to perform another calculation.
     *
     * @return {@code true} to continue; {@code false} to exit
     */
    private boolean promptContinue() {
        while (true) {
            System.out.print(colorize("Perform another calculation? (Y/N): ", YELLOW));
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
        System.out.println(colorize("  Thank you for using Basic Calculator!", GREEN));
        System.out.println(colorize("  Goodbye! Have a productive day.", GREEN));
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
     * Applies ANSI color codes when supported.
     *
     * @param text  the text to colorize
     * @param color the ANSI color prefix
     * @return colorized text or plain text if ANSI is unsupported
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

    /**
     * Formats a number for consistent display, removing unnecessary trailing zeros.
     *
     * @param value the number to format
     * @return formatted number string
     */
    private String formatNumber(double value) {
        if (value == (long) value) {
            return String.format(Locale.US, "%d", (long) value);
        }
        return String.format(Locale.US, "%.4f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    /**
     * Builds a history entry for binary operations.
     *
     * @param firstOperand  the first operand
     * @param symbol        the operation symbol
     * @param secondOperand the second operand
     * @param result        the computed result
     * @return formatted history string
     */
    private String formatBinaryHistory(double firstOperand, String symbol,
                                       double secondOperand, double result) {
        return String.format(Locale.US, "%s %s %s = %s",
                formatNumber(firstOperand),
                symbol,
                formatNumber(secondOperand),
                formatNumber(result));
    }

    /**
     * Functional interface for binary calculator operations.
     */
    @FunctionalInterface
    private interface BinaryOperation {
        /**
         * Applies a binary operation to two operands.
         *
         * @param firstOperand  the first operand
         * @param secondOperand the second operand
         * @return the computed result
         */
        double apply(double firstOperand, double secondOperand);
    }
}
