import exception.EmployeeNotFoundException;
import model.Employee;
import service.EmployeeService;
import util.InputValidator;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final int MENU_ADD = 1;
    private static final int MENU_VIEW = 2;
    private static final int MENU_SEARCH = 3;
    private static final int MENU_UPDATE = 4;
    private static final int MENU_DELETE = 5;
    private static final int MENU_STATISTICS = 6;
    private static final int MENU_EXIT = 7;

    private static final String APP_TITLE = "EMPLOYEE MANAGEMENT SYSTEM";
    private static final String BORDER = "=========================================";

    private static final boolean ANSI_SUPPORTED = System.console() != null;

    private static final String RESET = ANSI_SUPPORTED ? "\u001B[0m" : "";
    private static final String BOLD = ANSI_SUPPORTED ? "\u001B[1m" : "";
    private static final String CYAN = ANSI_SUPPORTED ? "\u001B[36m" : "";
    private static final String GREEN = ANSI_SUPPORTED ? "\u001B[32m" : "";
    private static final String YELLOW = ANSI_SUPPORTED ? "\u001B[33m" : "";
    private static final String RED = ANSI_SUPPORTED ? "\u001B[31m" : "";

    private static final double KEEP_SALARY = -1.0;

    private final EmployeeService employeeService;
    private final Scanner scanner;


    public Main() {
        this.employeeService = new EmployeeService();
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
            displayMenu();
            int option = readMenuOption();

            switch (option) {
                case MENU_ADD -> handleAddEmployee();
                case MENU_VIEW -> handleViewEmployees();
                case MENU_SEARCH -> handleSearchEmployee();
                case MENU_UPDATE -> handleUpdateEmployee();
                case MENU_DELETE -> handleDeleteEmployee();
                case MENU_STATISTICS -> handleStatistics();
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
        System.out.println(colorize("  Codveda Technologies — Java Internship (Level 2)", GREEN));
        System.out.println();
    }

    private void displayMenu() {
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  " + APP_TITLE, BOLD));
        System.out.println(colorize(BORDER, CYAN));
        System.out.println();
        System.out.println("  1. Add Employee");
        System.out.println("  2. View Employees");
        System.out.println("  3. Search Employee");
        System.out.println("  4. Update Employee");
        System.out.println("  5. Delete Employee");
        System.out.println("  6. Statistics");
        System.out.println("  7. Exit");
        System.out.println();
        System.out.print(colorize("Choose Option: ", YELLOW));
    }


    private int readMenuOption() {
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                if (InputValidator.isValidMenuOption(option)) {
                    return option;
                }

                printError("Please choose a number between 1 and 7.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a number.");
                System.out.print(colorize("Choose Option: ", YELLOW));
            }
        }
    }


    private void handleAddEmployee() {
        System.out.println();
        System.out.println(colorize("--- Add Employee ---", BOLD));
        System.out.println();

        try {
            String firstName = readNonEmptyString("First Name: ");
            String lastName = readNonEmptyString("Last Name: ");
            int age = readValidAge();
            String gender = readValidGender();
            String department = readNonEmptyString("Department: ");
            String designation = readNonEmptyString("Designation: ");
            double salary = readValidSalary();
            String email = readValidEmail(null);
            String phone = readValidPhone(null);

            Employee employee = new Employee(firstName, lastName, age, gender,
                    department, designation, salary, email, phone);

            employeeService.addEmployee(employee);
            printSuccess("Employee Added Successfully! ID: " + employee.getEmployeeId());
        } catch (IllegalArgumentException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    private void handleViewEmployees() {
        System.out.println();
        System.out.println(colorize("--- View Employees ---", BOLD));
        System.out.println();

        List<Employee> employees = employeeService.getAllEmployees();

        if (employees.isEmpty()) {
            printInfo("No employees found in the system.");
        } else {
            displayEmployeeTable(employees);
            System.out.println();
            printInfo("Total Employees: " + employeeService.getEmployeeCount());
        }

        System.out.println();
    }


    private void handleSearchEmployee() {
        System.out.println();
        System.out.println(colorize("--- Search Employee ---", BOLD));
        System.out.println();
        System.out.println("  1. Search by Employee ID");
        System.out.println("  2. Search by Name");
        System.out.println("  3. Search by Department");
        System.out.println();
        System.out.print(colorize("Choose Search Option: ", YELLOW));

        int searchOption = readSearchOption();
        List<Employee> results;

        switch (searchOption) {
            case 1 -> {
                String id = readNonEmptyString("Enter Employee ID: ");
                results = employeeService.searchById(id);
            }
            case 2 -> {
                String name = readNonEmptyString("Enter Name: ");
                results = employeeService.searchByName(name);
            }
            case 3 -> {
                String department = readNonEmptyString("Enter Department: ");
                results = employeeService.searchByDepartment(department);
            }
            default -> {
                printError("Invalid search option.");
                return;
            }
        }

        if (results.isEmpty()) {
            printError("Employee Not Found.");
        } else {
            displayEmployeeTable(results);
            printInfo("Found " + results.size() + " employee(s).");
        }

        System.out.println();
    }


    private int readSearchOption() {
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option >= 1 && option <= 3) {
                    return option;
                }
                printError("Please choose 1, 2, or 3.");
                System.out.print(colorize("Choose Search Option: ", YELLOW));
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a number.");
                System.out.print(colorize("Choose Search Option: ", YELLOW));
            }
        }
    }


    private void handleUpdateEmployee() {
        System.out.println();
        System.out.println(colorize("--- Update Employee ---", BOLD));
        System.out.println();

        if (!employeeService.hasEmployees()) {
            printInfo("No employees to update.");
            System.out.println();
            return;
        }

        String employeeId = readNonEmptyString("Enter Employee ID to update: ");

        try {
            Employee existing = employeeService.findById(employeeId);
            System.out.println();
            printInfo("Current Details:");
            System.out.println("  " + existing);
            System.out.println();
            printInfo("Leave field blank (or enter -1 for salary) to keep current value.");
            System.out.println();

            String firstName = readOptionalString("New First Name: ");
            String lastName = readOptionalString("New Last Name: ");
            String department = readOptionalString("New Department: ");
            String designation = readOptionalString("New Designation: ");
            double salary = readOptionalSalary();
            String email = readOptionalEmail(employeeId);
            String phone = readOptionalPhone(employeeId);

            Employee updated = employeeService.updateEmployee(
                    employeeId, firstName, lastName, department, designation,
                    salary, email, phone);

            printSuccess("Employee Updated Successfully!");
            System.out.println("  " + updated);
        } catch (EmployeeNotFoundException exception) {
            printError("Employee Not Found.");
        } catch (IllegalArgumentException exception) {
            printError(exception.getMessage());
        }

        System.out.println();
    }

    private void handleDeleteEmployee() {
        System.out.println();
        System.out.println(colorize("--- Delete Employee ---", BOLD));
        System.out.println();

        if (!employeeService.hasEmployees()) {
            printInfo("No employees to delete.");
            System.out.println();
            return;
        }

        String employeeId = readNonEmptyString("Enter Employee ID to delete: ");

        try {
            Employee employee = employeeService.findById(employeeId);
            System.out.println();
            printInfo("Employee to delete:");
            System.out.println("  " + employee);
            System.out.println();

            if (readConfirmation("Are you sure you want to delete this employee? (Y/N): ")) {
                employeeService.deleteEmployee(employeeId);
                printSuccess("Employee Deleted Successfully!");
            } else {
                printInfo("Deletion cancelled.");
            }
        } catch (EmployeeNotFoundException exception) {
            printError("Employee Not Found.");
        }

        System.out.println();
    }

    private void handleStatistics() {
        System.out.println();
        System.out.println(colorize("--- Statistics ---", BOLD));
        System.out.println();

        if (!employeeService.hasEmployees()) {
            printInfo("No employee data available.");
            System.out.println();
            return;
        }

        System.out.printf(Locale.US, "Total Employees  : %d%n", employeeService.getEmployeeCount());
        System.out.printf(Locale.US, "Highest Salary   : %.2f%n", employeeService.getHighestSalary());
        System.out.printf(Locale.US, "Lowest Salary    : %.2f%n", employeeService.getLowestSalary());
        System.out.printf(Locale.US, "Average Salary   : %.2f%n", employeeService.getAverageSalary());
        System.out.println();
        System.out.println(colorize("Department-wise Employee Count:", BOLD));

        Map<String, Integer> departmentCount = employeeService.getDepartmentWiseCount();
        for (Map.Entry<String, Integer> entry : departmentCount.entrySet()) {
            System.out.printf(Locale.US, "  %-20s : %d%n", entry.getKey(), entry.getValue());
        }

        System.out.println();
    }


    private void displayEmployeeTable(List<Employee> employees) {
        String horizontalLine = "-".repeat(140);
        System.out.println(horizontalLine);
        System.out.printf(Locale.US,
                "%-8s | %-12s | %-12s | %-4s | %-8s | %-14s | %-14s | %-10s | %-22s | %-12s%n",
                "ID", "First Name", "Last Name", "Age", "Gender", "Department", "Designation",
                "Salary", "Email", "Phone");
        System.out.println(horizontalLine);

        for (Employee employee : employees) {
            System.out.printf(Locale.US,
                    "%-8s | %-12s | %-12s | %-4d | %-8s | %-14s | %-14s | %10.2f | %-22s | %-12s%n",
                    employee.getEmployeeId(),
                    truncate(employee.getFirstName(), 12),
                    truncate(employee.getLastName(), 12),
                    employee.getAge(),
                    employee.getGender(),
                    truncate(employee.getDepartment(), 14),
                    truncate(employee.getDesignation(), 14),
                    employee.getSalary(),
                    truncate(employee.getEmail(), 22),
                    employee.getPhoneNumber());
        }

        System.out.println(horizontalLine);
    }


    private String truncate(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 2) + "..";
    }


    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(colorize(prompt, YELLOW));
            String input = scanner.nextLine().trim();

            if (InputValidator.isValidName(input)) {
                return input;
            }

            printError("Field cannot be empty. Please try again.");
        }
    }

  
    private String readOptionalString(String prompt) {
        System.out.print(colorize(prompt, YELLOW));
        return scanner.nextLine().trim();
    }


    private int readValidAge() {
        while (true) {
            try {
                System.out.print(colorize("Age (" + InputValidator.getMinAge()
                        + "-" + InputValidator.getMaxAge() + "): ", YELLOW));
                int age = scanner.nextInt();
                scanner.nextLine();

                if (InputValidator.isValidAge(age)) {
                    return age;
                }

                printError("Age must be between " + InputValidator.getMinAge()
                        + " and " + InputValidator.getMaxAge() + ".");
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a valid integer.");
            }
        }
    }


    private String readValidGender() {
        while (true) {
            System.out.print(colorize("Gender (Male/Female/Other): ", YELLOW));
            String gender = scanner.nextLine().trim();

            if (InputValidator.isValidGender(gender)) {
                return InputValidator.normalizeGender(gender);
            }

            printError("Invalid gender. Enter Male, Female, or Other.");
        }
    }


    private double readValidSalary() {
        while (true) {
            try {
                System.out.print(colorize("Salary: ", YELLOW));
                double salary = scanner.nextDouble();
                scanner.nextLine();

                if (InputValidator.isValidSalary(salary)) {
                    return salary;
                }

                printError("Salary cannot be negative.");
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a valid number.");
            }
        }
    }

    private double readOptionalSalary() {
        while (true) {
            try {
                System.out.print(colorize("New Salary (-1 to keep): ", YELLOW));
                double salary = scanner.nextDouble();
                scanner.nextLine();

                if (salary == KEEP_SALARY || InputValidator.isValidSalary(salary)) {
                    return salary;
                }

                printError("Salary cannot be negative.");
            } catch (InputMismatchException exception) {
                clearInvalidInput();
                printError("Invalid input. Please enter a valid number.");
            }
        }
    }


    private String readValidEmail(String excludeId) {
        while (true) {
            System.out.print(colorize("Email: ", YELLOW));
            String email = scanner.nextLine().trim();

            if (!InputValidator.isValidEmail(email)) {
                printError("Invalid email format. Example: name@company.com");
                continue;
            }

            return email;
        }
    }


    private String readOptionalEmail(String employeeId) {
        while (true) {
            System.out.print(colorize("New Email (blank to keep): ", YELLOW));
            String email = scanner.nextLine().trim();

            if (email.isBlank()) {
                return email;
            }

            if (!InputValidator.isValidEmail(email)) {
                printError("Invalid email format.");
                continue;
            }

            return email;
        }
    }


    private String readValidPhone(String excludeId) {
        while (true) {
            System.out.print(colorize("Phone Number (10 digits): ", YELLOW));
            String phone = scanner.nextLine().trim();

            if (!InputValidator.isValidPhoneNumber(phone)) {
                printError("Invalid phone number. Enter exactly "
                        + InputValidator.getPhoneNumberLength() + " digits.");
                continue;
            }

            return phone;
        }
    }


    private String readOptionalPhone(String employeeId) {
        while (true) {
            System.out.print(colorize("New Phone (blank to keep): ", YELLOW));
            String phone = scanner.nextLine().trim();

            if (phone.isBlank()) {
                return phone;
            }

            if (!InputValidator.isValidPhoneNumber(phone)) {
                printError("Invalid phone number. Enter exactly "
                        + InputValidator.getPhoneNumberLength() + " digits.");
                continue;
            }

            return phone;
        }
    }


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

    private void displayGoodbyeMessage() {
        System.out.println();
        System.out.println(colorize(BORDER, CYAN));
        System.out.println(colorize("  Thank you for using Employee Management System!", GREEN));
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
