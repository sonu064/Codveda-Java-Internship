import exception.FileProcessingException;
import model.FileStatistics;
import service.FileProcessor;
import util.FileValidator;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final int MENU_READ = 1;
    private static final int MENU_PROCESS = 2;
    private static final int MENU_VIEW_STATS = 3;
    private static final int MENU_EXPORT = 4;
    private static final int MENU_EXIT = 5;

    private static final String APP_TITLE = "FILE HANDLING SYSTEM";
    private static final String BORDER = "=========================================";

    private static final String INPUT_DIRECTORY = "input";
    private static final String OUTPUT_DIRECTORY = "output";
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String OUTPUT_FILE_NAME = "output.txt";
    private static final String PROJECT_MARKER_DIRECTORY = "src";

    private static final boolean ANSI_SUPPORTED = System.console() != null;

    private static final String RESET = ANSI_SUPPORTED ? "\u001B[0m" : "";
    private static final String BOLD = ANSI_SUPPORTED ? "\u001B[1m" : "";
    private static final String CYAN = ANSI_SUPPORTED ? "\u001B[36m" : "";
    private static final String GREEN = ANSI_SUPPORTED ? "\u001B[32m" : "";
    private static final String YELLOW = ANSI_SUPPORTED ? "\u001B[33m" : "";
    private static final String RED = ANSI_SUPPORTED ? "\u001B[31m" : "";

    private final FileProcessor fileProcessor;
    private final Scanner scanner;
    private final Path projectRoot;
    private final Path inputFilePath;
    private final Path outputFilePath;

    public Main() {
        this.fileProcessor = new FileProcessor();
        this.scanner = new Scanner(System.in);
        this.projectRoot = resolveProjectRoot();
        this.inputFilePath = projectRoot.resolve(INPUT_DIRECTORY).resolve(INPUT_FILE_NAME);
        this.outputFilePath = projectRoot.resolve(OUTPUT_DIRECTORY).resolve(OUTPUT_FILE_NAME);
    }

    private Path resolveProjectRoot() {
        List<Path> startingPoints = new ArrayList<>();
        startingPoints.add(Paths.get("").toAbsolutePath());

        try {
            Path codeSourceLocation = Paths.get(Main.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            startingPoints.add(codeSourceLocation);
        } catch (URISyntaxException | NullPointerException exception) {
            // Code source unavailable — fall back to the working directory only.
        }

        for (Path startingPoint : startingPoints) {
            Path root = searchUpwardForRoot(startingPoint);
            if (root != null) {
                return root;
            }
        }

        return Paths.get("").toAbsolutePath();
    }

    private Path searchUpwardForRoot(Path start) {
        Path current = start;
        while (current != null) {
            boolean hasSource = Files.isDirectory(current.resolve(PROJECT_MARKER_DIRECTORY));
            boolean hasInput = Files.isDirectory(current.resolve(INPUT_DIRECTORY));
            if (hasSource && hasInput) {
                return current;
            }
            current = current.getParent();
        }
        return null;
    }


    public static void main(String[] args) {
        Main application = new Main();
        application.run();
    }


    public void run() {
        displayWelcomeBanner();
        boolean running = true;

        while (running) {
            displayMenu();
            int option = readMenuOption();

            switch (option) {
                case MENU_READ -> handleReadFile();
                case MENU_PROCESS -> handleProcessFile();
                case MENU_VIEW_STATS -> handleViewStatistics();
                case MENU_EXPORT -> handleExportReport();
                case MENU_EXIT -> running = false;
                default -> printError("Invalid option selected.");
            }
        }

        displayGoodbyeMessage();
        scanner.close();
    }


    private void displayWelcomeBanner() {
        System.out.println();
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize(centerText(APP_TITLE, BORDER.length()), BOLD + CYAN));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  Reading and Writing to a File", GREEN));
        System.out.println(colorize("  Codveda Technologies — Java Internship (Level 2)", GREEN));
        System.out.println();
    }

    /**
vate void displayMenu() {
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  " + APP_TITLE, BOLD));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
        System.out.println("  1. Read File");
        System.out.println("  2. Process File");
        System.out.println("  3. View Statistics");
        System.out.println("  4. Export Report");
        System.out.println("  5. Exit");
        System.out.println();
        System.out.print(colorize("Choose Option: ", YELLOW));
    }

    /**
     * Reads and validates a menu option.
     *
     * @return valid menu option
     */
    private int readMenuOption() {
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                if (FileValidator.isValidMenuOption(option)) {
                    return option;
                }

                printError("Please choose a number between 1 and 5.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a number.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            }
        }
    }

    /**
     * Handles reading the input file.
     */
    private void handleReadFile() {
        System.out.println();
        System.out.println(colorize("Reading File...", YELLOW));
        System.out.println();

        try {
            fileProcessor.readFile(inputFilePath);
            printSuccess("File Loaded Successfully.");
            printInfo("Loaded " + fileProcessor.getLoadedLineCount() + " line(s) from "
                    + inputFilePath.toAbsolutePath());
        } catch (FileProcessingException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles processing the loaded file content.
     */
    private void handleProcessFile() {
        System.out.println();
        System.out.println(colorize("Processing File...", YELLOW));
        System.out.println();

        try {
            FileStatistics statistics = fileProcessor.processFile(
                    inputFilePath.getFileName().toString());
            printSuccess("File Processed Successfully.");
            displayStatistics(statistics);
        } catch (FileProcessingException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Displays computed statistics if available.
     */
    private void handleViewStatistics() {
        System.out.println();

        if (!fileProcessor.hasStatistics()) {
            printError("No statistics available. Please read and process the file first.");
        } else {
            displayStatistics(fileProcessor.getStatistics());
        }

        System.out.println();
    }

    /**
     * Exports the statistics report to the output file.
     */
    private void handleExportReport() {
        System.out.println();
        System.out.println(colorize("Exporting Report...", YELLOW));
        System.out.println();

        try {
            fileProcessor.writeReport(outputFilePath);
            printSuccess("Report Generated Successfully.");
            printInfo("Output saved to:");
            printInfo(outputFilePath.toAbsolutePath().toString());
        } catch (FileProcessingException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Displays file statistics on the console.
     *
     * @param statistics the statistics to display
     */
    private void displayStatistics(FileStatistics statistics) {
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("FILE STATISTICS", BOLD + CYAN));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
        System.out.printf("Total Lines        : %d%n", statistics.getTotalLines());
        System.out.printf("Total Words        : %d%n", statistics.getTotalWords());
        System.out.printf("Characters         : %d%n", statistics.getTotalCharacters());
        System.out.printf("Empty Lines        : %d%n", statistics.getEmptyLines());
        System.out.printf("Vowels             : %d%n", statistics.getVowels());
        System.out.printf("Consonants         : %d%n", statistics.getConsonants());
        System.out.printf("Digits             : %d%n", statistics.getDigits());
        System.out.println();
    }

    /**
     * Displays the goodbye message.
     */
    private void displayGoodbyeMessage() {
        System.out.println();
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  Thank you for using File Handling System!", GREEN));
        System.out.println(colorize("  Goodbye!", GREEN));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
    }

    /**
     * Prints a success message.
     *
     * @param message success text
     */
    private void printSuccess(String message) {
        System.out.println(colorize(message, BOLD + GREEN));
    }

    /**
     * Prints an error message.
     *
     * @param message error text
     */
    private void printError(String message) {
        System.out.println(colorize("Error: " + message, RED));
    }

    /**
     * Prints an informational message.
     *
     * @param message info text
     */
    private void printInfo(String message) {
        System.out.println(colorize(message, CYAN));
    }

    /**
     * Clears invalid scanner input.
     */
    private void clearInvalidInput() {
        scanner.nextLine();
    }

    /**
     * Applies ANSI color when supported.
     *
     * @param text  text to colorize
     * @param color ANSI color code
     * @return colorized text
     */
    private String colorize(String text, String color) {
        return color + text + RESET;
    }

    /**
     * Centers text within a given width.
     *
     * @param text  text to center
     * @param width total width
     * @return centered text
     */
    private String centerText(String text, int width) {
        int padding = Math.max(0, (width - text.length()) / 2);
        return " ".repeat(padding) + text;
    }
}
