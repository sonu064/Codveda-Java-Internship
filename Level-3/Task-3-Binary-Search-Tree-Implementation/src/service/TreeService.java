package service;

import bst.BinarySearchTree;

import java.util.List;
import java.util.stream.Collectors;


public class TreeService {

    private static final String EMPTY_TREE_MESSAGE = "The tree is empty. Insert some nodes first.";
    private static final String TRAVERSAL_SEPARATOR = " -> ";

    private final BinarySearchTree tree;


    public TreeService(BinarySearchTree tree) {
        this.tree = tree;
    }


    public String insertNode(int value) {
        return tree.insert(value)
                ? "Node " + value + " inserted successfully."
                : "Duplicate value: " + value + " already exists in the tree.";
    }

    public String deleteNode(int value) {
        if (tree.isEmpty()) {
            return "Cannot delete from an empty tree.";
        }
        return tree.delete(value)
                ? "Node " + value + " deleted successfully."
                : "Node " + value + " not found. Nothing was deleted.";
    }


    public String searchNode(int value) {
        if (tree.isEmpty()) {
            return "Node Not Found (the tree is empty).";
        }
        return tree.contains(value) ? "Node Found" : "Node Not Found";
    }

    public String getInorderTraversal() {
        return formatTraversal(tree.inorderTraversal());
    }


    public String getPreorderTraversal() {
        return formatTraversal(tree.preorderTraversal());
    }

    public String getPostorderTraversal() {
        return formatTraversal(tree.postorderTraversal());
    }


    public String getLevelOrderTraversal() {
        return formatTraversal(tree.levelOrderTraversal());
    }

    public String getTreeHeight() {
        if (tree.isEmpty()) {
            return EMPTY_TREE_MESSAGE;
        }
        return "Tree Height : " + tree.height() + " (edges on the longest root-to-leaf path)";
    }


    public String getNodeCount() {
        return "Total Nodes : " + tree.countNodes();
    }


    public String getMinimum() {
        if (tree.isEmpty()) {
            return EMPTY_TREE_MESSAGE;
        }
        return "Minimum Value : " + tree.findMin();
    }


    public String getMaximum() {
        if (tree.isEmpty()) {
            return EMPTY_TREE_MESSAGE;
        }
        return "Maximum Value : " + tree.findMax();
    }


    private String formatTraversal(List<Integer> values) {
        if (values.isEmpty()) {
            return EMPTY_TREE_MESSAGE;
        }
        return values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(TRAVERSAL_SEPARATOR));
    }
}
