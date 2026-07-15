import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import model.AccountType;
import model.BankAccount;
import model.Transaction;
import service.BankingService;
import util.InputValidator;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Entry point for the Simple Banking Application console system.
 * <p>
 * Handles all user interaction. Banking operations are delegated to {@link BankingService}.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class Main {

    private static final int MENU_CREATE = 1;
    private static final int MENU_DEPOSIT = 2;
    private static final int MENU_WITHDRAW = 3;
    private static final int MENU_TRANSFER = 4;
    private static final int MENU_BALANCE = 5;
    private static final int MENU_VIEW_ALL = 6;
    private static final int MENU_HISTORY = 7;
    private static final int MENU_DELETE = 8;
    private static final int MENU_EXIT = 9;

    private static final String APP_TITLE = "BANKING MANAGEMENT SYSTEM";
    private static final String BORDER = "=========================================";

    private static final boolean ANSI_SUPPORTED = System.console() != null;

    private static final String RESET = ANSI_SUPPORTED ? "\u001B[0m" : "";
    private static final String BOLD = ANSI_SUPPORTED ? "\u001B[1m" : "";
    private static final String CYAN = ANSI_SUPPORTED ? "\u001B[36m" : "";
    private static final String GREEN = ANSI_SUPPORTED ? "\u001B[32m" : "";
    private static final String YELLOW = ANSI_SUPPORTED ? "\u001B[33m" : "";
    private static final String RED = ANSI_SUPPORTED ? "\u001B[31m" : "";

    private final BankingService bankingService;
    private final Scanner scanner;

    /**
     * Constructs the application with initialized dependencies.
     */
    public Main() {
        this.bankingService = new BankingService();
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
     * Starts the main application loop.
     */
    public void run() {
        displayWelcomeBanner();
        boolean running = true;

        while (running) {
            displayMenu();
            int option = readMenuOption();

            switch (option) {
                case MENU_CREATE -> handleCreateAccount();
                case MENU_DEPOSIT -> handleDeposit();
                case MENU_WITHDRAW -> handleWithdraw();
                case MENU_TRANSFER -> handleTransfer();
                case MENU_BALANCE -> handleCheckBalance();
                case MENU_VIEW_ALL -> handleViewAllAccounts();
                case MENU_HISTORY -> handleTransactionHistory();
                case MENU_DELETE -> handleDeleteAccount();
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
        System.out.println(colorize("  Simple Banking Application", GREEN));
        System.out.println(colorize("  Codveda Technologies — Java Internship (Level 2)", GREEN));
        System.out.println();
    }

    /**
     * Displays the main menu.
     */
    private void displayMenu() {
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("      " + APP_TITLE, BOLD));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
        System.out.println("  1. Create Account");
        System.out.println("  2. Deposit Money");
        System.out.println("  3. Withdraw Money");
        System.out.println("  4. Transfer Money");
        System.out.println("  5. Check Balance");
        System.out.println("  6. View All Accounts");
        System.out.println("  7. Transaction History");
        System.out.println("  8. Delete Account");
        System.out.println("  9. Exit");
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

                printError("Please choose a number between 1 and 9.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a number.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            }
        }
    }

    /**
     * Handles creating a new bank account.
     */
    private void handleCreateAccount() {
        System.out.println();
        System.out.println(colorize("--- Create Bank Account ---", BOLD));
        System.out.println();

        try {
            String name = readNonEmptyString("Account Holder Name: ");
            String email = readValidEmail();
            String phone = readValidPhone();
            AccountType type = readAccountType();
            double initialBalance = readNonNegativeAmount("Initial Balance: ");

            BankAccount account = bankingService.createAccount(
                    name, email, phone, type, initialBalance);

            printSuccess("Account Created Successfully");
            printInfo("Account Number : " + account.getAccountNumber());
            printInfo("Account Type   : " + account.getAccountType().getDisplayName());
            printInfo("Current Balance: " + BankingService.formatCurrency(account.getBalance()));
        } catch (InvalidAmountException exception) {
            printError(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles depositing money into an account.
     */
    private void handleDeposit() {
        System.out.println();
        System.out.println(colorize("--- Deposit Money ---", BOLD));
        System.out.println();

        if (!bankingService.hasAccounts()) {
            printInfo("No accounts available. Please create an account first.");
            System.out.println();
            return;
        }

        try {
            String accountNumber = readNonEmptyString("Account Number: ");
            double amount = readPositiveAmount("Deposit Amount: ");

            BankAccount account = bankingService.deposit(accountNumber, amount);
            printSuccess("Deposit Successful");
            printInfo("Current Balance : " + BankingService.formatCurrency(account.getBalance()));
        } catch (AccountNotFoundException exception) {
            printError(exception.getMessage());
        } catch (InvalidAmountException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles withdrawing money from an account.
     */
    private void handleWithdraw() {
        System.out.println();
        System.out.println(colorize("--- Withdraw Money ---", BOLD));
        System.out.println();

        if (!bankingService.hasAccounts()) {
            printInfo("No accounts available. Please create an account first.");
            System.out.println();
            return;
        }

        try {
            String accountNumber = readNonEmptyString("Account Number: ");
            double amount = readPositiveAmount("Withdrawal Amount: ");

            BankAccount account = bankingService.withdraw(accountNumber, amount);
            printSuccess("Withdrawal Successful");
            printInfo("Remaining Balance : " + BankingService.formatCurrency(account.getBalance()));
        } catch (AccountNotFoundException exception) {
            printError(exception.getMessage());
        } catch (InvalidAmountException exception) {
            printError(exception.getMessage());
        } catch (InsufficientBalanceException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles transferring money between accounts.
     */
    private void handleTransfer() {
        System.out.println();
        System.out.println(colorize("--- Transfer Money ---", BOLD));
        System.out.println();

        if (bankingService.getAccountCount() < 2) {
            printInfo("At least two accounts are required for a transfer.");
            System.out.println();
            return;
        }

        try {
            String fromAccount = readNonEmptyString("From Account Number: ");
            String toAccount = readNonEmptyString("To Account Number: ");
            double amount = readPositiveAmount("Transfer Amount: ");

            bankingService.transfer(fromAccount, toAccount, amount);
            printSuccess("Transfer Successful");

            BankAccount sender = bankingService.findAccount(fromAccount);
            BankAccount receiver = bankingService.findAccount(toAccount);
            printInfo("Sender Balance   : " + BankingService.formatCurrency(sender.getBalance()));
            printInfo("Receiver Balance : " + BankingService.formatCurrency(receiver.getBalance()));
        } catch (AccountNotFoundException exception) {
            printError(exception.getMessage());
        } catch (InvalidAmountException exception) {
            printError(exception.getMessage());
        } catch (InsufficientBalanceException exception) {
            printError(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles checking account balance and details.
     */
    private void handleCheckBalance() {
        System.out.println();
        System.out.println(colorize("--- Check Balance ---", BOLD));
        System.out.println();

        if (!bankingService.hasAccounts()) {
            printInfo("No accounts available.");
            System.out.println();
            return;
        }

        try {
            String accountNumber = readNonEmptyString("Account Number: ");
            BankAccount account = bankingService.findAccount(accountNumber);
            displayAccountDetails(account);
        } catch (AccountNotFoundException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Handles viewing all accounts in table format.
     */
    private void handleViewAllAccounts() {
        System.out.println();
        System.out.println(colorize("--- View All Accounts ---", BOLD));
        System.out.println();

        List<BankAccount> accounts = bankingService.getAllAccounts();

        if (accounts.isEmpty()) {
            printInfo("No accounts found.");
        } else {
            displayAccountTable(accounts);
            printInfo("Total Accounts: " + bankingService.getAccountCount());
        }

        System.out.println();
    }

    /**
     * Handles viewing transaction history.
     */
    private void handleTransactionHistory() {
        System.out.println();
        System.out.println(colorize("--- Transaction History ---", BOLD));
        System.out.println();

        System.out.println("  1. All Transactions");
        System.out.println("  2. By Account Number");
        System.out.println();
        System.out.print(colorize("Choose Option: ", YELLOW));

        int choice = readSubMenuOption(2);
        List<Transaction> history;

        if (choice == 1) {
            history = bankingService.getTransactionHistory(null);
        } else {
            String accountNumber = readNonEmptyString("Account Number: ");
            history = bankingService.getTransactionHistory(accountNumber);
        }

        if (history.isEmpty()) {
            printInfo("No transactions found.");
        } else {
            System.out.println();
            for (int index = 0; index < history.size(); index++) {
                System.out.printf("  %d. %s%n", index + 1, history.get(index));
            }
            printInfo("Total Transactions: " + history.size());
        }

        System.out.println();
    }

    /**
     * Handles deleting an account.
     */
    private void handleDeleteAccount() {
        System.out.println();
        System.out.println(colorize("--- Delete Account ---", BOLD));
        System.out.println();

        if (!bankingService.hasAccounts()) {
            printInfo("No accounts to delete.");
            System.out.println();
            return;
        }

        try {
            String accountNumber = readNonEmptyString("Account Number: ");
            BankAccount account = bankingService.findAccount(accountNumber);

            System.out.println();
            displayAccountDetails(account);
            System.out.println();

            if (account.getBalance() > 0) {
                printInfo("Warning: Account has a remaining balance of "
                        + BankingService.formatCurrency(account.getBalance()));
            }

            if (readConfirmation("Are you sure you want to delete this account? (Y/N): ")) {
                bankingService.deleteAccount(accountNumber);
                printSuccess("Account Deleted Successfully");
            } else {
                printInfo("Deletion cancelled.");
            }
        } catch (AccountNotFoundException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    /**
     * Displays a single account's full details.
     *
     * @param account the account to display
     */
    private void displayAccountDetails(BankAccount account) {
        System.out.println(colorize("--- Account Details ---", BOLD));
        System.out.printf("Account Number  : %s%n", account.getAccountNumber());
        System.out.printf("Holder Name     : %s%n", account.getAccountHolderName());
        System.out.printf("Email           : %s%n", account.getEmail());
        System.out.printf("Phone           : %s%n", account.getPhoneNumber());
        System.out.printf("Account Type    : %s%n", account.getAccountType().getDisplayName());
        System.out.printf("Balance         : %s%n", BankingService.formatCurrency(account.getBalance()));
        System.out.printf("Created Date    : %s%n", account.getCreatedDate());
    }

    /**
     * Displays accounts in a formatted table.
     *
     * @param accounts list of accounts
     */
    private void displayAccountTable(List<BankAccount> accounts) {
        String line = "-".repeat(120);
        System.out.println(line);
        System.out.printf(Locale.US,
                "%-10s | %-18s | %-22s | %-12s | %-10s | %-12s | %-12s%n",
                "Acc No.", "Holder Name", "Email", "Phone", "Type", "Balance", "Created");
        System.out.println(line);

        for (BankAccount account : accounts) {
            System.out.printf(Locale.US,
                    "%-10s | %-18s | %-22s | %-12s | %-10s | %12s | %-12s%n",
                    account.getAccountNumber(),
                    truncate(account.getAccountHolderName(), 18),
                    truncate(account.getEmail(), 22),
                    account.getPhoneNumber(),
                    account.getAccountType().getDisplayName(),
                    BankingService.formatCurrency(account.getBalance()),
                    account.getCreatedDate());
        }

        System.out.println(line);
    }

    /**
     * Reads a sub-menu option within a range.
     *
     * @param maxOption maximum valid option
     * @return valid option
     */
    private int readSubMenuOption(int maxOption) {
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option >= 1 && option <= maxOption) {
                    return option;
                }
                printError("Please choose between 1 and " + maxOption + ".");
                System.out.print(colorize("Choose Option: ", YELLOW));
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a number.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            }
        }
    }

    /**
     * Reads a non-empty string.
     *
     * @param prompt input prompt
     * @return non-empty string
     */
    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(colorize(prompt, YELLOW));
            String input = scanner.nextLine().trim();
            if (InputValidator.isValidName(input)) {
                return input;
            }
            printError("Field cannot be empty.");
        }
    }

    /**
     * Reads a valid email address.
     *
     * @return valid email
     */
    private String readValidEmail() {
        while (true) {
            System.out.print(colorize("Email: ", YELLOW));
            String email = scanner.nextLine().trim();
            if (InputValidator.isValidEmail(email)) {
                return email;
            }
            printError("Invalid email format. Example: name@bank.com");
        }
    }

    /**
     * Reads a valid 10-digit phone number.
     *
     * @return valid phone number
     */
    private String readValidPhone() {
        while (true) {
            System.out.print(colorize("Phone Number (10 digits): ", YELLOW));
            String phone = scanner.nextLine().trim();
            if (InputValidator.isValidPhoneNumber(phone)) {
                return phone;
            }
            printError("Invalid phone. Enter exactly "
                    + InputValidator.getPhoneNumberLength() + " digits.");
        }
    }

    /**
     * Reads a valid account type.
     *
     * @return account type
     */
    private AccountType readAccountType() {
        while (true) {
            System.out.print(colorize("Account Type (Savings/Current): ", YELLOW));
            String input = scanner.nextLine().trim();
            try {
                return AccountType.fromString(input);
            } catch (IllegalArgumentException exception) {
                printError("Invalid type. Enter Savings or Current.");
            }
        }
    }

    /**
     * Reads a positive monetary amount.
     *
     * @param prompt input prompt
     * @return positive amount
     */
    private double readPositiveAmount(String prompt) {
        while (true) {
            try {
                System.out.print(colorize(prompt, YELLOW));
                double amount = scanner.nextDouble();
                scanner.nextLine();
                if (InputValidator.isValidAmount(amount)) {
                    return amount;
                }
                printError("Amount must be greater than zero.");
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a non-negative monetary amount (for initial balance).
     *
     * @param prompt input prompt
     * @return non-negative amount
     */
    private double readNonNegativeAmount(String prompt) {
        while (true) {
            try {
                System.out.print(colorize(prompt, YELLOW));
                double amount = scanner.nextDouble();
                scanner.nextLine();
                if (InputValidator.isValidInitialBalance(amount)) {
                    return amount;
                }
                printError("Balance cannot be negative.");
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a Y/N confirmation.
     *
     * @param prompt confirmation prompt
     * @return {@code true} if confirmed
     */
    private boolean readConfirmation(String prompt) {
        while (true) {
            System.out.print(colorize(prompt, YELLOW));
            String response = scanner.nextLine().trim();
            if (!InputValidator.isValidConfirmation(response)) {
                printError("Please enter Y or N.");
                continue;
            }
            return InputValidator.isConfirmed(response);
        }
    }

    /**
     * Truncates a string for table display.
     *
     * @param value     string value
     * @param maxLength max length
     * @return truncated string
     */
    private String truncate(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 2) + "..";
    }

    /**
     * Displays the goodbye message.
     */
    private void displayGoodbyeMessage() {
        System.out.println();
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  Thank you for using Banking Management System!", GREEN));
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
