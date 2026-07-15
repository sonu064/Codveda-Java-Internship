package bst;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BinarySearchTree {


    private TreeNode root;


    public BinarySearchTree() {
        this.root = null;
    }

    public boolean insert(int value) {
        if (contains(value)) {
            return false;
        }
        root = insertRecursive(root, value);
        return true;
    }


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


    public boolean contains(int value) {
        return containsRecursive(root, value);
    }

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


    public boolean delete(int value) {
        if (!contains(value)) {
            return false;
        }
        root = deleteRecursive(root, value);
        return true;
    }


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

        if (current.isLeaf()) {
            return null;
        }
        if (current.getLeft() == null) {
            return current.getRight();
        }
        if (current.getRight() == null) {
            return current.getLeft();
        }

        int successorValue = findMinRecursive(current.getRight());
        current.setValue(successorValue);
        current.setRight(deleteRecursive(current.getRight(), successorValue));
        return current;
    }
    public List<Integer> inorderTraversal() {
        List<Integer> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }

    private void inorderRecursive(TreeNode current, List<Integer> result) {
        if (current == null) {
            return;
        }
        inorderRecursive(current.getLeft(), result);
        result.add(current.getValue());
        inorderRecursive(current.getRight(), result);
    }


    public List<Integer> preorderTraversal() {
        List<Integer> result = new ArrayList<>();
        preorderRecursive(root, result);
        return result;
    }


    private void preorderRecursive(TreeNode current, List<Integer> result) {
        if (current == null) {
            return;
        }
        result.add(current.getValue());
        preorderRecursive(current.getLeft(), result);
        preorderRecursive(current.getRight(), result);
    }

    public List<Integer> postorderTraversal() {
        List<Integer> result = new ArrayList<>();
        postorderRecursive(root, result);
        return result;
    }

    private void postorderRecursive(TreeNode current, List<Integer> result) {
        if (current == null) {
            return;
        }
        postorderRecursive(current.getLeft(), result);
        postorderRecursive(current.getRight(), result);
        result.add(current.getValue());
    }


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


    public int height() {
        return heightRecursive(root);
    }


    private int heightRecursive(TreeNode current) {
        if (current == null) {
            return -1;
        }
        return 1 + Math.max(
                heightRecursive(current.getLeft()),
                heightRecursive(current.getRight()));
    }


    public int countNodes() {
        return countNodesRecursive(root);
    }


    private int countNodesRecursive(TreeNode current) {
        if (current == null) {
            return 0;
        }
        return 1
                + countNodesRecursive(current.getLeft())
                + countNodesRecursive(current.getRight());
    }


    public int findMin() {
        if (root == null) {
            throw new NoSuchElementException("Cannot find minimum: the tree is empty.");
        }
        return findMinRecursive(root);
    }


    private int findMinRecursive(TreeNode current) {
        return current.getLeft() == null
                ? current.getValue()
                : findMinRecursive(current.getLeft());
    }


    public int findMax() {
        if (root == null) {
            throw new NoSuchElementException("Cannot find maximum: the tree is empty.");
        }
        return findMaxRecursive(root);
    }

    private int findMaxRecursive(TreeNode current) {
        return current.getRight() == null
                ? current.getValue()
                : findMaxRecursive(current.getRight());
    }

    public boolean isEmpty() {
        return root == null;
    }
}
