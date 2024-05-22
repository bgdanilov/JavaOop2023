package ru.bgdanilov.trees;

public class BinaryTree<T> {
    TreeNode<T> root;

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public BinaryTree() {}
    public BinaryTree(TreeNode<T> root) {
        this.root = root;
    }

    public void add(T data) {
        if (root == null) {
            root = new TreeNode<>(data);
        } else {

        }


    }
}
