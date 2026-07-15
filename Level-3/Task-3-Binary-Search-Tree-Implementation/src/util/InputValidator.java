package util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Validates and safely reads console input for the BST application.
 * <p>
 * The {@link Scanner} is provided via constructor injection, so this class
 * holds no static mutable state. Every read method loops until valid input
 * is supplied, guaranteeing the application never crashes on bad input
 * (letters, symbols, out-of-range menu options, or values beyond the
 * {@code int} range).
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class InputValidator {

    private final Scanner scanner;

    /**
     * Creates a validator reading from the given scanner.
     *
     * @param scanner the input source (typically wrapping {@code System.in})
     */
    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Reads a menu choice within the given inclusive range, re-prompting on
     * invalid input.
     *
     * @param minOption the smallest valid option
     * @param maxOption the largest valid option
     * @return a validated menu choice
     */
    public int readMenuChoice(int minOption, int maxOption) {
        while (true) {
            ConsoleHelper.printPrompt("Choose Option (" + minOption + "-" + maxOption + ") : ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= minOption && choice <= maxOption) {
                    return choice;
                }
                ConsoleHelper.printError(
                        "Please choose an option between " + minOption + " and " + maxOption + ".");
            } catch (InputMismatchException exception) {
                scanner.nextLine();
                ConsoleHelper.printError("Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * Reads any integer value (negative and large values within the
     * {@code int} range are accepted), re-prompting on invalid input.
     *
     * @param prompt the prompt to display
     * @return a validated integer
     */
    public int readInteger(String prompt) {
        while (true) {
            ConsoleHelper.printPrompt(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException exception) {
                scanner.nextLine();
                ConsoleHelper.printError("Invalid input. Please enter a whole number between "
                        + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + ".");
            }
        }
    }
}
