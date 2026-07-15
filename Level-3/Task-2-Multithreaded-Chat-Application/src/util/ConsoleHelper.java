package util;

/**
 * Console output utility for the Multithreaded Chat Application.
 * <p>
 * Centralizes banner drawing, colored status messages, and screen clearing so
 * that server and client classes never duplicate formatting logic. ANSI colors
 * are applied only when a real terminal is attached.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class ConsoleHelper {

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
        String border = String.valueOf(Constants.BANNER_BORDER_CHAR)
                .repeat(Constants.BANNER_WIDTH);
        int padding = Math.max(0, (Constants.BANNER_WIDTH - title.length()) / 2);

        System.out.println();
        System.out.println(CYAN + border + RESET);
        System.out.println(BOLD + CYAN + " ".repeat(padding) + title + RESET);
        System.out.println(CYAN + border + RESET);
        System.out.println();
    }

    /**
     * Prints an informational status message.
     *
     * @param message the message text
     */
    public static void printInfo(String message) {
        System.out.println(GREEN + message + RESET);
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
     * Prints a chat message exactly as formatted by the sender.
     *
     * @param formattedMessage the pre-formatted chat message
     */
    public static void printChat(String formattedMessage) {
        System.out.println(formattedMessage);
    }

    /**
     * Prints an input prompt without a trailing newline.
     *
     * @param prompt the prompt text
     */
    public static void printPrompt(String prompt) {
        System.out.print(YELLOW + prompt + RESET);
    }

    /**
     * Clears the console screen.
     * <p>
     * Uses the ANSI clear sequence when a terminal is attached; otherwise
     * prints blank lines as a portable fallback.
     * </p>
     */
    public static void clearScreen() {
        if (ANSI_SUPPORTED) {
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();
        } else {
            for (int line = 0; line < 50; line++) {
                System.out.println();
            }
        }
    }
}
