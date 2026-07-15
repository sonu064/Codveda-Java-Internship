package com.codveda.library;

import config.DBConnection;
import dao.BookDAO;
import dao.BorrowDAO;
import dao.UserDAO;
import exception.BookNotFoundException;
import exception.DatabaseException;
import exception.UserNotFoundException;
import model.Book;
import model.BorrowTransaction;
import model.User;
import service.LibraryService;
import util.InputValidator;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Entry point for the Library Management System console application.
 * <p>
 * Handles user interaction; business logic is delegated to {@link LibraryService}.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class Main {

    private static final int MENU_ADD_BOOK = 1;
    private static final int MENU_VIEW_BOOKS = 2;
    private static final int MENU_SEARCH_BOOK = 3;
    private static final int MENU_UPDATE_BOOK = 4;
    private static final int MENU_DELETE_BOOK = 5;
    private static final int MENU_REGISTER_USER = 6;
    private static final int MENU_VIEW_USERS = 7;
    private static final int MENU_BORROW = 8;
    private static final int MENU_RETURN = 9;
    private static final int MENU_HISTORY = 10;
    private static final int MENU_EXIT = 11;

    private static final String APP_TITLE = "LIBRARY MANAGEMENT SYSTEM";
    private static final String BORDER = "=========================================";

    private static final boolean ANSI_SUPPORTED = System.console() != null;

    private static final String RESET = ANSI_SUPPORTED ? "\u001B[0m" : "";
    private static final String BOLD = ANSI_SUPPORTED ? "\u001B[1m" : "";
    private static final String CYAN = ANSI_SUPPORTED ? "\u001B[36m" : "";
    private static final String GREEN = ANSI_SUPPORTED ? "\u001B[32m" : "";
    private static final String YELLOW = ANSI_SUPPORTED ? "\u001B[33m" : "";
    private static final String RED = ANSI_SUPPORTED ? "\u001B[31m" : "";

    private final LibraryService libraryService;
    private final Scanner scanner;

    /**
     * Constructs the application with dependency injection.
     */
    public Main() {
        BookDAO bookDAO = new BookDAO();
        UserDAO userDAO = new UserDAO();
        BorrowDAO borrowDAO = new BorrowDAO(bookDAO);
        this.libraryService = new LibraryService(bookDAO, userDAO, borrowDAO);
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
     * Starts the application loop.
     */
    public void run() {
        displayWelcomeBanner();

        try {
            DBConnection.testConnection();
            printSuccess("Database Connected Successfully.");
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
            Throwable rootCause = exception.getCause();
            if (rootCause != null) {
                printError("Root cause: " + rootCause.getClass().getName() + ": " + rootCause.getMessage());
                rootCause.printStackTrace(System.err);
            } else {
                exception.printStackTrace(System.err);
            }
            printInfo("Please configure MySQL credentials in DBConnection.java and run database scripts.");
            return;
        }

        boolean running = true;
        while (running) {
            displayMenu();
            int option = readMenuOption();

            switch (option) {
                case MENU_ADD_BOOK -> handleAddBook();
                case MENU_VIEW_BOOKS -> handleViewBooks();
                case MENU_SEARCH_BOOK -> handleSearchBook();
                case MENU_UPDATE_BOOK -> handleUpdateBook();
                case MENU_DELETE_BOOK -> handleDeleteBook();
                case MENU_REGISTER_USER -> handleRegisterUser();
                case MENU_VIEW_USERS -> handleViewUsers();
                case MENU_BORROW -> handleBorrowBook();
                case MENU_RETURN -> handleReturnBook();
                case MENU_HISTORY -> handleBorrowHistory();
                case MENU_EXIT -> running = false;
                default -> printError("Invalid option selected.");
            }
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
        System.out.println(colorize("  JDBC + MySQL Library System", GREEN));
        System.out.println(colorize("  Codveda Technologies — Java Internship (Level 3)", GREEN));
        System.out.println();
    }

    /**
     * Displays the main menu.
     */
    private void displayMenu() {
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  " + APP_TITLE, BOLD));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
        System.out.println("  1.  Add Book");
        System.out.println("  2.  View Books");
        System.out.println("  3.  Search Book");
        System.out.println("  4.  Update Book");
        System.out.println("  5.  Delete Book");
        System.out.println("  6.  Register User");
        System.out.println("  7.  View Users");
        System.out.println("  8.  Borrow Book");
        System.out.println("  9.  Return Book");
        System.out.println("  10. Borrow History");
        System.out.println("  11. Exit");
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
                if (InputValidator.isValidMenuOption(option)) {
                    return option;
                }
                printError("Please choose a number between 1 and 11.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a number.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            }
        }
    }

    /**
     * Handles adding a new book.
     */
    private void handleAddBook() {
        System.out.println();
        System.out.println(colorize("--- Add Book ---", BOLD));
        System.out.println();

        try {
            String title = readNonBlank("Title: ");
            String author = readNonBlank("Author: ");
            String category = readNonBlank("Category: ");
            String isbn = readNonBlank("ISBN: ");
            int quantity = readPositiveInt("Quantity: ");

            Book book = new Book(title, author, category, isbn, quantity, quantity);
            Book created = libraryService.addBook(book);

            printSuccess("Book Added Successfully");
            printInfo("Book ID: " + created.getBookId() + " | " + created.getTitle());
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles viewing all books.
     */
    private void handleViewBooks() {
        System.out.println();
        System.out.println(colorize("--- View Books ---", BOLD));
        System.out.println();

        try {
            List<Book> books = libraryService.getAllBooks();
            if (books.isEmpty()) {
                printInfo("No books found.");
            } else {
                displayBookTable(books);
                printInfo("Total Books: " + books.size());
            }
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles searching books.
     */
    private void handleSearchBook() {
        System.out.println();
        System.out.println(colorize("--- Search Book ---", BOLD));
        System.out.println();

        String keyword = readNonBlank("Search (title/author/category/ISBN): ");

        try {
            List<Book> results = libraryService.searchBooks(keyword);
            if (results.isEmpty()) {
                printInfo("No books found matching: " + keyword);
            } else {
                displayBookTable(results);
                printInfo("Found " + results.size() + " book(s).");
            }
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles updating a book.
     */
    private void handleUpdateBook() {
        System.out.println();
        System.out.println(colorize("--- Update Book ---", BOLD));
        System.out.println();

        try {
            int bookId = readPositiveInt("Book ID: ");
            Book existing = libraryService.findBook(bookId);

            System.out.println();
            printInfo("Current: " + existing);
            printInfo("Leave blank to keep current value.");
            System.out.println();

            String title = readOptional("New Title: ", existing.getTitle());
            String author = readOptional("New Author: ", existing.getAuthor());
            String category = readOptional("New Category: ", existing.getCategory());
            String isbn = readOptional("New ISBN: ", existing.getIsbn());
            int quantity = readOptionalPositiveInt("New Quantity (-1 to keep): ", existing.getQuantity());
            int available = readOptionalPositiveInt("New Available (-1 to keep): ", existing.getAvailableQuantity());

            existing.setTitle(title);
            existing.setAuthor(author);
            existing.setCategory(category);
            existing.setIsbn(isbn);
            if (quantity > 0) {
                existing.setQuantity(quantity);
            }
            if (available >= 0) {
                existing.setAvailableQuantity(available);
            }

            libraryService.updateBook(existing);
            printSuccess("Book Updated Successfully");
            printInfo(existing.toString());
        } catch (BookNotFoundException exception) {
            printError(exception.getMessage());
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles deleting a book.
     */
    private void handleDeleteBook() {
        System.out.println();
        System.out.println(colorize("--- Delete Book ---", BOLD));
        System.out.println();

        try {
            int bookId = readPositiveInt("Book ID: ");
            Book book = libraryService.findBook(bookId);

            System.out.println();
            printInfo(book.toString());
            System.out.println();

            if (readConfirmation("Delete this book? (Y/N): ")) {
                libraryService.deleteBook(bookId);
                printSuccess("Book Deleted Successfully");
            } else {
                printInfo("Deletion cancelled.");
            }
        } catch (BookNotFoundException exception) {
            printError(exception.getMessage());
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles user registration.
     */
    private void handleRegisterUser() {
        System.out.println();
        System.out.println(colorize("--- Register User ---", BOLD));
        System.out.println();

        try {
            String name = readNonBlank("Full Name: ");
            String email = readValidEmail();
            String phone = readValidPhone();

            User user = new User(name, email, phone);
            User registered = libraryService.registerUser(user);

            printSuccess("User Registered Successfully");
            printInfo("User ID: " + registered.getUserId() + " | " + registered.getFullName());
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles viewing all users.
     */
    private void handleViewUsers() {
        System.out.println();
        System.out.println(colorize("--- View Users ---", BOLD));
        System.out.println();

        try {
            List<User> users = libraryService.getAllUsers();
            if (users.isEmpty()) {
                printInfo("No users registered.");
            } else {
                displayUserTable(users);
                printInfo("Total Users: " + users.size());
            }
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles borrowing a book.
     */
    private void handleBorrowBook() {
        System.out.println();
        System.out.println(colorize("--- Borrow Book ---", BOLD));
        System.out.println();

        try {
            int userId = readPositiveInt("User ID: ");
            int bookId = readPositiveInt("Book ID: ");

            int transactionId = libraryService.borrowBook(userId, bookId);
            printSuccess("Book Borrowed Successfully");
            printInfo("Transaction ID: " + transactionId);
        } catch (UserNotFoundException | BookNotFoundException exception) {
            printError(exception.getMessage());
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles returning a borrowed book.
     */
    private void handleReturnBook() {
        System.out.println();
        System.out.println(colorize("--- Return Book ---", BOLD));
        System.out.println();

        try {
            int transactionId = readPositiveInt("Transaction ID: ");
            libraryService.returnBook(transactionId);
            printSuccess("Book Returned Successfully");
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles viewing borrow history.
     */
    private void handleBorrowHistory() {
        System.out.println();
        System.out.println(colorize("--- Borrow History ---", BOLD));
        System.out.println();
        System.out.println("  1. All Transactions");
        System.out.println("  2. By User ID");
        System.out.println();
        System.out.print(colorize("Choose Option: ", YELLOW));

        int choice = readSubMenuOption(2);
        List<BorrowTransaction> history;

        try {
            if (choice == 1) {
                history = libraryService.getAllBorrowHistory();
            } else {
                int userId = readPositiveInt("User ID: ");
                history = libraryService.getBorrowHistoryByUser(userId);
            }

            if (history.isEmpty()) {
                printInfo("No transactions found.");
            } else {
                System.out.println();
                for (int i = 0; i < history.size(); i++) {
                    System.out.printf("  %d. %s%n", i + 1, history.get(i));
                }
                printInfo("Total: " + history.size() + " transaction(s).");
            }
        } catch (UserNotFoundException exception) {
            printError(exception.getMessage());
        } catch (DatabaseException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Displays books in a formatted table.
     *
     * @param books list of books
     */
    private void displayBookTable(List<Book> books) {
        String line = "-".repeat(120);
        System.out.println(line);
        System.out.printf(Locale.US, "%-6s | %-25s | %-18s | %-14s | %-16s | %-6s | %-6s%n",
                "ID", "Title", "Author", "Category", "ISBN", "Qty", "Avail");
        System.out.println(line);

        for (Book book : books) {
            System.out.printf(Locale.US, "%-6d | %-25s | %-18s | %-14s | %-16s | %6d | %6d%n",
                    book.getBookId(),
                    truncate(book.getTitle(), 25),
                    truncate(book.getAuthor(), 18),
                    truncate(book.getCategory(), 14),
                    truncate(book.getIsbn(), 16),
                    book.getQuantity(),
                    book.getAvailableQuantity());
        }
        System.out.println(line);
    }

    /**
     * Displays users in a formatted table.
     *
     * @param users list of users
     */
    private void displayUserTable(List<User> users) {
        String line = "-".repeat(90);
        System.out.println(line);
        System.out.printf(Locale.US, "%-6s | %-22s | %-28s | %-12s%n",
                "ID", "Full Name", "Email", "Phone");
        System.out.println(line);

        for (User user : users) {
            System.out.printf(Locale.US, "%-6d | %-22s | %-28s | %-12s%n",
                    user.getUserId(),
                    truncate(user.getFullName(), 22),
                    truncate(user.getEmail(), 28),
                    user.getPhone());
        }
        System.out.println(line);
    }

    private int readSubMenuOption(int max) {
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option >= 1 && option <= max) {
                    return option;
                }
                printError("Please choose between 1 and " + max + ".");
                System.out.print(colorize("Choose Option: ", YELLOW));
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            }
        }
    }

    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(colorize(prompt, YELLOW));
            String input = scanner.nextLine().trim();
            if (InputValidator.isNotBlank(input)) {
                return input;
            }
            printError("Field cannot be empty.");
        }
    }

    private String readOptional(String prompt, String current) {
        System.out.print(colorize(prompt, YELLOW));
        String input = scanner.nextLine().trim();
        return input.isBlank() ? current : input;
    }

    private String readValidEmail() {
        while (true) {
            System.out.print(colorize("Email: ", YELLOW));
            String email = scanner.nextLine().trim();
            if (InputValidator.isValidEmail(email)) {
                return email;
            }
            printError("Invalid email format.");
        }
    }

    private String readValidPhone() {
        while (true) {
            System.out.print(colorize("Phone (10 digits): ", YELLOW));
            String phone = scanner.nextLine().trim();
            if (InputValidator.isValidPhone(phone)) {
                return phone;
            }
            printError("Invalid phone. Enter " + InputValidator.getPhoneNumberLength() + " digits.");
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            try {
                System.out.print(colorize(prompt, YELLOW));
                int value = scanner.nextInt();
                scanner.nextLine();
                if (InputValidator.isPositiveId(value) || InputValidator.isPositiveQuantity(value)) {
                    return value;
                }
                printError("Value must be greater than zero.");
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Enter a number.");
            }
        }
    }

    private int readOptionalPositiveInt(String prompt, int current) {
        while (true) {
            try {
                System.out.print(colorize(prompt, YELLOW));
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value == -1) {
                    return -1;
                }
                if (value >= 0) {
                    return value;
                }
                printError("Enter -1 to keep, or a non-negative number.");
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input.");
            }
        }
    }

    private boolean readConfirmation(String prompt) {
        while (true) {
            System.out.print(colorize(prompt, YELLOW));
            String response = scanner.nextLine().trim().toUpperCase();
            if (response.equals("Y") || response.equals("N")) {
                return response.equals("Y");
            }
            printError("Enter Y or N.");
        }
    }

    private String truncate(String value, int max) {
        return value.length() <= max ? value : value.substring(0, max - 2) + "..";
    }

    private void displayGoodbyeMessage() {
        System.out.println();
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  Thank you for using Library Management System!", GREEN));
        System.out.println(colorize("  Goodbye!", GREEN));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
    }

    private void printSuccess(String message) {
        System.out.println(colorize(message, BOLD + GREEN));
    }

    private void printError(String message) {
        System.out.println(colorize("Error: " + message, RED));
    }

    private void printInfo(String message) {
        System.out.println(colorize(message, CYAN));
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
