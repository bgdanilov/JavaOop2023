package ru.bgdanilov.trees;

import java.util.*;

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

    // 1. Вставка.
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


    // 2. Поиск узла.
    public boolean contains(TreeNode<T> node) {
        TreeNode<T> parentNode = root;
        TreeNode<T> currentNode = root;

        while (true) {
            if (node.getData().compareTo(currentNode.getData()) == 0) {
                System.out.println(parentNode.getData().toString());
                return true;
            }

            if (node.getData().compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getLeft();
                } else {
                    return false;
                }
            } else {
                if (currentNode.getRight() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getRight();
                } else {
                    return false;
                }
            }
        }
    }

    public void delete(TreeNode<T> node) {
        TreeNode<T> parentNode = root;
        TreeNode<T> currentNode = root;

        TreeNode<T> deletedNode;
        TreeNode<T> deletedNodeParent;
        TreeNode<T> minLeftNode;
        TreeNode<T> minLeftNodeParent;

        while (true) {
            if (node.getData().compareTo(currentNode.getData()) == 0) {
                // 1. Лист. Нет детей.
                if (currentNode.getLeft() == null && currentNode.getRight() == null) {
                    if (parentNode.getLeft() != null) {
                        parentNode.setLeft(null);
                    } else if (parentNode.getRight() != null) {
                        parentNode.setRight(null);
                    }
                }

                // 2. Только один ребенок.
                if ((currentNode.getLeft() != null && currentNode.getRight() == null)
                || currentNode.getLeft() == null && currentNode.getRight() != null) {
                    if (parentNode.getLeft() == currentNode) { // найденный узел - левый ребенок родителя;
                        parentNode.setLeft(currentNode.getLeft() != null ? currentNode.getLeft() : currentNode.getRight());
                    }

                    if (parentNode.getRight() == currentNode) { // найденный узел - правый ребенок родителя;
                        parentNode.setRight(currentNode.getLeft() != null ? currentNode.getLeft() : currentNode.getRight());
                    }
                }

                // 3. Два ребенка.
                // Найти в правом поддереве минимальный элемент и переместить его на место удаляемого узла.
                if (currentNode.getLeft() != null && currentNode.getRight() != null) {
                   minLeftNode = getMin(currentNode);
                }
            }


            // Поиск узла.
            if (node.getData().compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getLeft();
                } else {
                    return;
                }
            } else {
                if (currentNode.getRight() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getRight();
                } else {
                    return;
                }
            }
        }
    }

    private TreeNode<T> getMin(TreeNode<T> startNode) {
        return null;
    }

    private boolean hasChildren(TreeNode<T> node) {
        return node.getLeft() != null || node.getRight() != null;
    }

    // TODO вставить └ символ. Не корректно работает если удалить Аллу.
    @Override
    public String toString() {
        Stack<TreeNode<T>> treeNodeStack = new Stack<>();
        treeNodeStack.add(root);

        Stack<String> spacersStack = new Stack<>();
        StringBuilder spacerBuilder = new StringBuilder();
        spacersStack.add(spacerBuilder.toString());

        StringBuilder printTreeBuilder = new StringBuilder();

        String lineSeparator = System.lineSeparator();

        while (treeNodeStack.size() != 0) {
            TreeNode<T> currentNode = treeNodeStack.pop(); // берем верх и удаляем.
            printTreeBuilder
                    .append(spacersStack.pop())
                    .append(currentNode.getData().toString())
                    .append(lineSeparator);

            if (currentNode.getRight() != null || currentNode.getLeft() != null) {
                spacerBuilder.append("-");
            } else {
                spacerBuilder.delete(0, 1);
            }

            if (currentNode.getRight() != null) {
                treeNodeStack.add(currentNode.getRight());
                spacersStack.add(spacerBuilder.toString());
            }

            if (currentNode.getLeft() != null) {
                treeNodeStack.add(currentNode.getLeft());
                spacersStack.add(spacerBuilder.toString());
            }
        }

        return printTreeBuilder.toString();
    }
}