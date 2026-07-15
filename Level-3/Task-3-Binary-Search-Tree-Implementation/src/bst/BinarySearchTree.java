package bst;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * A fully recursive Binary Search Tree of unique integers.
 * <p>
 * Supports insertion, deletion (leaf, one-child, two-children, and root
 * cases), searching, the three depth-first traversals, a queue-based
 * level-order traversal, height calculation, node counting, and
 * minimum/maximum lookup.
 * </p>
 * <p>
 * Duplicate values are rejected. All operations run in O(h) or O(n) time,
 * where {@code h} is the tree height and {@code n} the number of nodes.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class BinarySearchTree {

    /** Root of the tree; {@code null} when the tree is empty. */
    private TreeNode root;

    /**
     * Creates an empty Binary Search Tree.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    // ------------------------------------------------------------------
    // Insert
    // ------------------------------------------------------------------

    /**
     * Inserts a value into the tree.
     *
     * @param value the value to insert
     * @return {@code true} if inserted; {@code false} if the value already exists
     */
    public boolean insert(int value) {
        if (contains(value)) {
            return false;
        }
        root = insertRecursive(root, value);
        return true;
    }

    /**
     * Recursively finds the correct position and links a new leaf node.
     *
     * @param current root of the current subtree
     * @param value   the value to insert
     * @return the (possibly new) root of this subtree
     */
    private TreeNode insertRecursive(TreeNode current, int value) {
        if (current == null) {
            return new TreeNode(value);
        }
        if (value < current.getValue()) {
            current.setLeft(insertRecursive(current.getLeft(), value));
        } else {
            current.setRight(insertRecursive(current.getRight(), value));
        }
        return current;
    }

    // ------------------------------------------------------------------
    // Search
    // ------------------------------------------------------------------

    /**
     * Searches the tree for a value.
     *
     * @param value the value to look for
     * @return {@code true} if the value exists in the tree
     */
    public boolean contains(int value) {
        return containsRecursive(root, value);
    }

    /**
     * Recursively descends toward the value using BST ordering.
     *
     * @param current root of the current subtree
     * @param value   the value to look for
     * @return {@code true} if found in this subtree
     */
    private boolean containsRecursive(TreeNode current, int value) {
        if (current == null) {
            return false;
        }
        if (value == current.getValue()) {
            return true;
        }
        return value < current.getValue()
                ? containsRecursive(current.getLeft(), value)
                : containsRecursive(current.getRight(), value);
    }

    // ------------------------------------------------------------------
    // Delete
    // ------------------------------------------------------------------

    /**
     * Deletes a value from the tree, correctly handling leaf nodes, nodes
     * with one child, nodes with two children, and the root itself.
     *
     * @param value the value to delete
     * @return {@code true} if deleted; {@code false} if the value was not found
     */
    public boolean delete(int value) {
        if (!contains(value)) {
            return false;
        }
        root = deleteRecursive(root, value);
        return true;
    }

    /**
     * Recursively locates and unlinks the node holding the value.
     * <ul>
     *   <li><b>Leaf:</b> replaced by {@code null}.</li>
     *   <li><b>One child:</b> replaced by that child.</li>
     *   <li><b>Two children:</b> value replaced by the inorder successor
     *       (smallest value in the right subtree), which is then deleted
     *       from the right subtree.</li>
     * </ul>
     * Because the recursion starts at {@code root}, root deletion needs no
     * special case — the method simply returns the new subtree root.
     *
     * @param current root of the current subtree
     * @param value   the value to delete
     * @return the new root of this subtree
     */
    private TreeNode deleteRecursive(TreeNode current, int value) {
        if (current == null) {
            return null;
        }

        if (value < current.getValue()) {
            current.setLeft(deleteRecursive(current.getLeft(), value));
            return current;
        }
        if (value > current.getValue()) {
            current.setRight(deleteRecursive(current.getRight(), value));
            return current;
        }

        // Found the node to delete.
        if (current.isLeaf()) {
            return null;
        }
        if (current.getLeft() == null) {
            return current.getRight();
        }
        if (current.getRight() == null) {
            return current.getLeft();
        }

        // Two children: copy inorder successor's value, then remove it.
        int successorValue = findMinRecursive(current.getRight());
        current.setValue(successorValue);
        current.setRight(deleteRecursive(current.getRight(), successorValue));
        return current;
    }

    // ------------------------------------------------------------------
    // Traversals
    // ------------------------------------------------------------------

    /**
     * Returns the inorder traversal (left, node, right) — sorted ascending.
     *
     * @return values in inorder sequence
     */
    public List<Integer> inorderTraversal() {
        List<Integer> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }

    /**
     * Recursive inorder helper.
     *
     * @param current root of the current subtree
     * @param result  accumulator for visited values
     */
    private void inorderRecursive(TreeNode current, List<Integer> result) {
        if (current == null) {
            return;
        }
        inorderRecursive(current.getLeft(), result);
        result.add(current.getValue());
        inorderRecursive(current.getRight(), result);
    }

    /**
     * Returns the preorder traversal (node, left, right).
     *
     * @return values in preorder sequence
     */
    public List<Integer> preorderTraversal() {
        List<Integer> result = new ArrayList<>();
        preorderRecursive(root, result);
        return result;
    }

    /**
     * Recursive preorder helper.
     *
     * @param current root of the current subtree
     * @param result  accumulator for visited values
     */
    private void preorderRecursive(TreeNode current, List<Integer> result) {
        if (current == null) {
            return;
        }
        result.add(current.getValue());
        preorderRecursive(current.getLeft(), result);
        preorderRecursive(current.getRight(), result);
    }

    /**
     * Returns the postorder traversal (left, right, node).
     *
     * @return values in postorder sequence
     */
    public List<Integer> postorderTraversal() {
        List<Integer> result = new ArrayList<>();
        postorderRecursive(root, result);
        return result;
    }

    /**
     * Recursive postorder helper.
     *
     * @param current root of the current subtree
     * @param result  accumulator for visited values
     */
    private void postorderRecursive(TreeNode current, List<Integer> result) {
        if (current == null) {
            return;
        }
        postorderRecursive(current.getLeft(), result);
        postorderRecursive(current.getRight(), result);
        result.add(current.getValue());
    }

    /**
     * Returns the level-order (breadth-first) traversal using a queue.
     *
     * @return values level by level, left to right
     */
    public List<Integer> levelOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            result.add(current.getValue());

            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }
        }
        return result;
    }

    // ------------------------------------------------------------------
    // Metrics
    // ------------------------------------------------------------------

    /**
     * Returns the height of the tree in edges.
     * <p>
     * A single-node tree has height 0; an empty tree has height -1.
     * </p>
     *
     * @return the tree height in edges
     */
    public int height() {
        return heightRecursive(root);
    }

    /**
     * Recursive height helper.
     *
     * @param current root of the current subtree
     * @return height of this subtree in edges, or -1 if empty
     */
    private int heightRecursive(TreeNode current) {
        if (current == null) {
            return -1;
        }
        return 1 + Math.max(
                heightRecursive(current.getLeft()),
                heightRecursive(current.getRight()));
    }

    /**
     * Counts every node in the tree.
     *
     * @return total number of nodes
     */
    public int countNodes() {
        return countNodesRecursive(root);
    }

    /**
     * Recursive node-count helper.
     *
     * @param current root of the current subtree
     * @return number of nodes in this subtree
     */
    private int countNodesRecursive(TreeNode current) {
        if (current == null) {
            return 0;
        }
        return 1
                + countNodesRecursive(current.getLeft())
                + countNodesRecursive(current.getRight());
    }

    /**
     * Returns the smallest value in the tree.
     *
     * @return the minimum value
     * @throws NoSuchElementException if the tree is empty
     */
    public int findMin() {
        if (root == null) {
            throw new NoSuchElementException("Cannot find minimum: the tree is empty.");
        }
        return findMinRecursive(root);
    }

    /**
     * Recursively follows left links to the smallest value.
     *
     * @param current root of the current subtree (never {@code null})
     * @return smallest value in this subtree
     */
    private int findMinRecursive(TreeNode current) {
        return current.getLeft() == null
                ? current.getValue()
                : findMinRecursive(current.getLeft());
    }

    /**
     * Returns the largest value in the tree.
     *
     * @return the maximum value
     * @throws NoSuchElementException if the tree is empty
     */
    public int findMax() {
        if (root == null) {
            throw new NoSuchElementException("Cannot find maximum: the tree is empty.");
        }
        return findMaxRecursive(root);
    }

    /**
     * Recursively follows right links to the largest value.
     *
     * @param current root of the current subtree (never {@code null})
     * @return largest value in this subtree
     */
    private int findMaxRecursive(TreeNode current) {
        return current.getRight() == null
                ? current.getValue()
                : findMaxRecursive(current.getRight());
    }

    /**
     * Reports whether the tree contains no nodes.
     *
     * @return {@code true} if the tree is empty
     */
    public boolean isEmpty() {
        return root == null;
    }
}
