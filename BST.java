//Chevannese Ellis, 2301109, March 17, 2025, BinarySearchTree.java

//package pack;

import java.util.Iterator;

public class BinarySearchTree<T extends Comparable<T>> implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private TreeNode<T> root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(T key, T value) {
        root = insertRecursive(root, key, value);
    }

    private TreeNode<T> insertRecursive(TreeNode<T> root, T key, T value) {
        if (root == null) {
            return new TreeNode<>(key, value);
        }

        int compareResult = key.compareTo(root.key);

        if (compareResult < 0) {
            root.left = insertRecursive(root.left, key, value);
        } else if (compareResult > 0) {
            root.right = insertRecursive(root.right, key, value);
        } else {
            // Handle duplicate keys by updating the value
            root.value = value;
        }

        return root;
    }

    public T search(T key) {
        TreeNode<T> result = searchRecursive(root, key);
        return (result != null) ? result.value : null;
    }

    private TreeNode<T> searchRecursive(TreeNode<T> root, T key) {
        if (root == null) {
            return null;
        }

        int compareResult = key.compareTo(root.key);

        if (compareResult < 0) {
            return searchRecursive(root.left, key);
        } else if (compareResult > 0) {
            return searchRecursive(root.right, key);
        } else {
            return root;
        }
    }

    public void displayTree() {
        displayHelper(root);
    }

    private void displayHelper(TreeNode<T> root) {
        if (root != null) {
            displayHelper(root.left);
            System.out.println(root.value);
            displayHelper(root.right);
        }
    }
}


class TreeNode<T> {
    public T key;
    public T value;
    public TreeNode<T> left;
    public TreeNode<T> right;

    public TreeNode(T key, T value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
