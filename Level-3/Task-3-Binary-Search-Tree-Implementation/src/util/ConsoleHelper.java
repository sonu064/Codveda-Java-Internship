package util;

/**
 * Console output utility for the Binary Search Tree application.
 * <p>
 * Centralizes banner drawing, menu rendering, and colored status messages so
 * the UI layer never duplicates formatting logic. ANSI colors are applied
 * only when a real terminal is attached.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class ConsoleHelper {

    private static final int BANNER_WIDTH = 45;
    private static final char BANNER_BORDER_CHAR = '=';

    private static final boolean ANSI_SUPPORTED = System.console() != null;

    private static final String RESET = ANSI_SUPPORTED ? "\u001B[0m" : "";
    private static final String BOLD = ANSI_SUPPORTED ? "\u001B[1m" : "";
    private static final String CYAN = ANSI_SUPPORTED ? "\u001B[36m" : "";
    private static final String GREEN = ANSI_SUPPORTED ? "\u001B[32m" : "";
    private static final String YELLOW = ANSI_SUPPORTED ? "\u001B[33m" : "";
    private static final String RED = ANSI_SUPPORTED ? "\u001B[31m" : "";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ConsoleHelper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Prints a bordered banner with a centered title.
     *
     * @param title the banner title
     */
    public static void printBanner(String title) {
        String border = String.valueOf(BANNER_BORDER_CHAR).repeat(BANNER_WIDTH);
        int padding = Math.max(0, (BANNER_WIDTH - title.length()) / 2);

        System.out.println();
        System.out.println(CYAN + border + RESET);
        System.out.println(BOLD + CYAN + " ".repeat(padding) + title + RESET);
        System.out.println(CYAN + border + RESET);
    }

    /**
     * Prints the main menu of the application.
     */
    public static void printMenu() {
        printBanner("BINARY SEARCH TREE IMPLEMENTATION");
        System.out.println(" 1.  Insert Node");
        System.out.println(" 2.  Delete Node");
        System.out.println(" 3.  Search Node");
        System.out.println(" 4.  Inorder Traversal");
        System.out.println(" 5.  Preorder Traversal");
        System.out.println(" 6.  Postorder Traversal");
        System.out.println(" 7.  Level Order Traversal");
        System.out.println(" 8.  Tree Height");
        System.out.println(" 9.  Count Nodes");
        System.out.println("10.  Find Minimum");
        System.out.println("11.  Find Maximum");
        System.out.println("12.  Exit");
        System.out.println(String.valueOf(BANNER_BORDER_CHAR).repeat(BANNER_WIDTH));
    }

    /**
     * Prints a success/result message.
     *
     * @param message the message text
     */
    public static void printSuccess(String message) {
        System.out.println(GREEN + message + RESET);
    }

    /**
     * Prints an informational message.
     *
     * @param message the message text
     */
    public static void printInfo(String message) {
        System.out.println(CYAN + message + RESET);
    }

    /**
     * Prints an error message.
     *
     * @param message the error text
     */
    public static void printError(String message) {
        System.out.println(RED + "Error: " + message + RESET);
    }

    /**
     * Prints an input prompt without a trailing newline.
     *
     * @param prompt the prompt text
     */
    public static void printPrompt(String prompt) {
        System.out.print(YELLOW + prompt + RESET);
    }
}
