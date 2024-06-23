package ru.bgdanilov.trees;

import java.util.*;
import java.util.function.Consumer;

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

    // 1. Вставка узла.
    public void add(T data) {
        if (root == null) {
            setRoot(new TreeNode<>(data));
            return;
        }

        TreeNode<T> currentNode = root;

        while (true) {
            if (data.compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode.setLeft(new TreeNode<>(data));
                    return;
                }
                // Узел больше текущего.
            } else { // а если равны?
                if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode.setRight(new TreeNode<>(data));
                    return;
                }
            }
        }
    }

    // 2. Поиск узла.
    public boolean contains(T data) {
        final boolean[] result = {false};

        allInDepth(treeNode -> {
            if (treeNode.getData().equals(data)) {
                result[0] = true;
            }
        });

        return result[0];
    }

    // 3. Удаление первого вхождения узла по значению.
    // Не реализовано.


    // 4. Получение числа элементов.
    public int getNodesAmount() {
        final int[] nodesAmount = {0};
        // Реализуем нагрузку в виде подсчета узлов.
        allInDepth(treeNode -> nodesAmount[0]++);

        return nodesAmount[0];
    }

    // 5. Обходы.
    // 5.1. В глубину - это Стек, в Ширину - это Очередь.
    public void printInDepth() {
        allInDepth(treeNode -> System.out.println(treeNode.getData()));
    }

    private void allInDepth(Consumer<TreeNode<T>> consumer) {
        Stack<TreeNode<T>> treeNodeStack = new Stack<>();
        treeNodeStack.push(root);

        while (!treeNodeStack.isEmpty()) {
            TreeNode<T> currentNode = treeNodeStack.pop();
            consumer.accept(currentNode);

            if (currentNode.getRight() != null) {
                treeNodeStack.push(currentNode.getRight());
            }

            if (currentNode.getLeft() != null) {
                treeNodeStack.push(currentNode.getLeft());
            }
        }
    }

    // 5.2. В Ширину - это Очередь.
    public void printInWide() {
        allInWide(treeNode -> System.out.println(treeNode.getData()));
    }

    private void allInWide(Consumer<TreeNode<T>> consumer) {
        Deque<TreeNode<T>> treeNodeQueue = new ArrayDeque<>();
        treeNodeQueue.add(root);

        while (!treeNodeQueue.isEmpty()) {
            TreeNode<T> currentNode = treeNodeQueue.remove();
            consumer.accept(currentNode);

            if (currentNode.getLeft() != null) {
                treeNodeQueue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                treeNodeQueue.add(currentNode.getRight());
            }
        }
    }

    // Раз мы не можем передать в функцию другую функцию как аргумент
    // - только объекты можем передать.
    // Тогда передадим экземпляр printLoad функционального интерфейса Consumer,
    // переопределив его единственный метод.
}