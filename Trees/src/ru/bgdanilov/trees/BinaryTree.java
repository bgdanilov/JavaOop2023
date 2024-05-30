package ru.bgdanilov.trees;

import java.util.ArrayList;

public class BinaryTree<T extends Comparable<T>> {
    TreeNode<T> root;

    public BinaryTree() {
    }

    public BinaryTree(TreeNode<T> root) {
        this.root = root;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public void add(TreeNode<T> node) {// Почему нельзя добавить <T extends Comparable<T>> ?
        TreeNode<T> currentNode = root;

        while (true) {
            if (node.getData().compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode.setLeft(node);
                    return;
                }
                // Узел больше текущего.
            } else { // а если равны?
                if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode.setRight(node);
                    return;
                }
            }
        }
    }

    public boolean find(TreeNode<T> node) {
        TreeNode<T> currentNode = root;

        while (true) {
            if (node.getData().compareTo(currentNode.getData()) == 0) {
                return true;
            }

            if (node.getData().compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                } else {
                    return false;
                }
            } else {
                if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else {
                    return false;
                }
            }
        }
    }

    @Override
    public String toString() {
        ArrayList<TreeNode<T>> queue = new ArrayList<>();
        queue.add(root);

        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        while (queue.size() != 0) {
            TreeNode<T> currentNode = queue.get(0);

            // Напечатали - удалили.
            sb.append(currentNode.getData().toString()).append(lineSeparator);
            queue.remove(0);

            if (currentNode.getRight() != null) {
                queue.add(0, currentNode.getRight());
            }

            if (currentNode.getLeft() != null) {
                queue.add(0, currentNode.getLeft());
            }
        }

        return sb.toString();
    }
}