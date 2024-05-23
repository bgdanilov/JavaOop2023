package ru.bgdanilov.trees;

public class BinaryTree<T> {
    TreeNode<T> root;
    public BinaryTree() {}
    public BinaryTree(TreeNode<T> root) {
        this.root = root;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public int getComparisonValue(T data) {
        int value = 0;

        if (root == null) {
            root = new TreeNode<>(data);
        } else {
            // Cannot resolve method 'compareTo' in 'T'
            // Что ему мешает?
            // value = root.getData().compareTo(data); // снимите комментарий.
        }

        return value;
    }
}