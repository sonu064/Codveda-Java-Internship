package service;

import bst.BinarySearchTree;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Application service layer between the console UI and the BST.
 * <p>
 * Translates raw tree operations into user-friendly result messages and
 * guards every operation against edge cases (empty tree, duplicates,
 * missing values). The tree instance is provided via constructor injection
 * so the service holds no static mutable state and is easy to test.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class TreeService {

    private static final String EMPTY_TREE_MESSAGE = "The tree is empty. Insert some nodes first.";
    private static final String TRAVERSAL_SEPARATOR = " -> ";

    private final BinarySearchTree tree;

    /**
     * Creates a service operating on the given tree.
     *
     * @param tree the Binary Search Tree to manage
     */
    public TreeService(BinarySearchTree tree) {
        this.tree = tree;
    }

    /**
     * Inserts a value, rejecting duplicates.
     *
     * @param value the value to insert
     * @return a user-friendly result message
     */
    public String insertNode(int value) {
        return tree.insert(value)
                ? "Node " + value + " inserted successfully."
                : "Duplicate value: " + value + " already exists in the tree.";
    }

    /**
     * Deletes a value, handling empty-tree and not-found cases.
     *
     * @param value the value to delete
     * @return a user-friendly result message
     */
    public String deleteNode(int value) {
        if (tree.isEmpty()) {
            return "Cannot delete from an empty tree.";
        }
        return tree.delete(value)
                ? "Node " + value + " deleted successfully."
                : "Node " + value + " not found. Nothing was deleted.";
    }

    /**
     * Searches for a value.
     *
     * @param value the value to find
     * @return {@code "Node Found"} or {@code "Node Not Found"}
     */
    public String searchNode(int value) {
        if (tree.isEmpty()) {
            return "Node Not Found (the tree is empty).";
        }
        return tree.contains(value) ? "Node Found" : "Node Not Found";
    }

    /**
     * Returns the inorder traversal as display text.
     *
     * @return formatted traversal or an empty-tree message
     */
    public String getInorderTraversal() {
        return formatTraversal(tree.inorderTraversal());
    }

    /**
     * Returns the preorder traversal as display text.
     *
     * @return formatted traversal or an empty-tree message
     */
    public String getPreorderTraversal() {
        return formatTraversal(tree.preorderTraversal());
    }

    /**
     * Returns the postorder traversal as display text.
     *
     * @return formatted traversal or an empty-tree message
     */
    public String getPostorderTraversal() {
        return formatTraversal(tree.postorderTraversal());
    }

    /**
     * Returns the level-order traversal as display text.
     *
     * @return formatted traversal or an empty-tree message
     */
    public String getLevelOrderTraversal() {
        return formatTraversal(tree.levelOrderTraversal());
    }

    /**
     * Returns the tree height in edges.
     *
     * @return height description or an empty-tree message
     */
    public String getTreeHeight() {
        if (tree.isEmpty()) {
            return EMPTY_TREE_MESSAGE;
        }
        return "Tree Height : " + tree.height() + " (edges on the longest root-to-leaf path)";
    }

    /**
     * Returns the total node count.
     *
     * @return node count description
     */
    public String getNodeCount() {
        return "Total Nodes : " + tree.countNodes();
    }

    /**
     * Returns the minimum value in the tree.
     *
     * @return minimum description or an empty-tree message
     */
    public String getMinimum() {
        if (tree.isEmpty()) {
            return EMPTY_TREE_MESSAGE;
        }
        return "Minimum Value : " + tree.findMin();
    }

    /**
     * Returns the maximum value in the tree.
     *
     * @return maximum description or an empty-tree message
     */
    public String getMaximum() {
        if (tree.isEmpty()) {
            return EMPTY_TREE_MESSAGE;
        }
        return "Maximum Value : " + tree.findMax();
    }

    /**
     * Joins traversal values with arrows, or reports an empty tree.
     *
     * @param values the traversal result
     * @return formatted traversal string
     */
    private String formatTraversal(List<Integer> values) {
        if (values.isEmpty()) {
            return EMPTY_TREE_MESSAGE;
        }
        return values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(TRAVERSAL_SEPARATOR));
    }
}
