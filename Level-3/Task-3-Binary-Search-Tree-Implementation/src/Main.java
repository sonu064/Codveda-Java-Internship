import bst.BinarySearchTree;
import service.TreeService;
import util.ConsoleHelper;
import util.InputValidator;

import java.util.Scanner;


public class Main {

    private static final int OPTION_INSERT = 1;
    private static final int OPTION_DELETE = 2;
    private static final int OPTION_SEARCH = 3;
    private static final int OPTION_INORDER = 4;
    private static final int OPTION_PREORDER = 5;
    private static final int OPTION_POSTORDER = 6;
    private static final int OPTION_LEVEL_ORDER = 7;
    private static final int OPTION_HEIGHT = 8;
    private static final int OPTION_COUNT = 9;
    private static final int OPTION_MIN = 10;
    private static final int OPTION_MAX = 11;
    private static final int OPTION_EXIT = 12;

    private final TreeService treeService;
    private final InputValidator inputValidator;


    public Main(TreeService treeService, InputValidator inputValidator) {
        this.treeService = treeService;
        this.inputValidator = inputValidator;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TreeService treeService = new TreeService(new BinarySearchTree());
            InputValidator inputValidator = new InputValidator(scanner);

            new Main(treeService, inputValidator).run();
        }
    }

    public void run() {
        boolean running = true;

        while (running) {
            ConsoleHelper.printMenu();
            int choice = inputValidator.readMenuChoice(OPTION_INSERT, OPTION_EXIT);
            System.out.println();

            try {
                running = handleChoice(choice);
            } catch (Exception exception) {
                ConsoleHelper.printError("Unexpected error: " + exception.getMessage());
            }
        }
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case OPTION_INSERT -> {
                int value = inputValidator.readInteger("Enter value to insert : ");
                ConsoleHelper.printSuccess(treeService.insertNode(value));
            }
            case OPTION_DELETE -> {
                int value = inputValidator.readInteger("Enter value to delete : ");
                ConsoleHelper.printSuccess(treeService.deleteNode(value));
            }
            case OPTION_SEARCH -> {
                int value = inputValidator.readInteger("Enter value to search : ");
                ConsoleHelper.printSuccess(treeService.searchNode(value));
            }
            case OPTION_INORDER -> {
                ConsoleHelper.printInfo("Inorder Traversal (Left -> Node -> Right):");
                ConsoleHelper.printSuccess(treeService.getInorderTraversal());
            }
            case OPTION_PREORDER -> {
                ConsoleHelper.printInfo("Preorder Traversal (Node -> Left -> Right):");
                ConsoleHelper.printSuccess(treeService.getPreorderTraversal());
            }
            case OPTION_POSTORDER -> {
                ConsoleHelper.printInfo("Postorder Traversal (Left -> Right -> Node):");
                ConsoleHelper.printSuccess(treeService.getPostorderTraversal());
            }
            case OPTION_LEVEL_ORDER -> {
                ConsoleHelper.printInfo("Level Order Traversal (top to bottom, left to right):");
                ConsoleHelper.printSuccess(treeService.getLevelOrderTraversal());
            }
            case OPTION_HEIGHT -> ConsoleHelper.printSuccess(treeService.getTreeHeight());
            case OPTION_COUNT -> ConsoleHelper.printSuccess(treeService.getNodeCount());
            case OPTION_MIN -> ConsoleHelper.printSuccess(treeService.getMinimum());
            case OPTION_MAX -> ConsoleHelper.printSuccess(treeService.getMaximum());
            case OPTION_EXIT -> {
                ConsoleHelper.printInfo("Thank you for using the BST application. Goodbye!");
                return false;
            }
            default -> ConsoleHelper.printError("Unknown option: " + choice);
        }
        return true;
    }
}
