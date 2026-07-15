package bst;

/**
 * A single node of the Binary Search Tree.
 * <p>
 * Each node stores one integer value and references to its left and right
 * subtrees. This class is a pure data holder — all tree logic (insertion,
 * deletion, searching, traversals) lives in {@link BinarySearchTree},
 * keeping responsibilities cleanly separated.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class TreeNode {

    /** The value stored in this node. */
    private int value;

    /** Root of the left subtree (values smaller than {@code value}). */
    private TreeNode left;

    /** Root of the right subtree (values greater than {@code value}). */
    private TreeNode right;

    /**
     * Creates a leaf node holding the given value.
     *
     * @param value the value to store in this node
     */
    public TreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    /**
     * Returns the value stored in this node.
     *
     * @return the node's value
     */
    public int getValue() {
        return value;
    }

    /**
     * Replaces the value stored in this node.
     * <p>
     * Used by the delete operation when copying the inorder successor's
     * value into a node that has two children.
     * </p>
     *
     * @param value the new value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Returns the root of the left subtree.
     *
     * @return the left child, or {@code null} if none exists
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * Sets the root of the left subtree.
     *
     * @param left the new left child (may be {@code null})
     */
    public void setLeft(TreeNode left) {
        this.left = left;
    }

    /**
     * Returns the root of the right subtree.
     *
     * @return the right child, or {@code null} if none exists
     */
    public TreeNode getRight() {
        return right;
    }

    /**
     * Sets the root of the right subtree.
     *
     * @param right the new right child (may be {@code null})
     */
    public void setRight(TreeNode right) {
        this.right = right;
    }

    /**
     * Reports whether this node has no children.
     *
     * @return {@code true} if both children are {@code null}
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Returns the node's value as text.
     *
     * @return string form of the stored value
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
