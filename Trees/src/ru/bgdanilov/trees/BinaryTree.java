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
    public void add(TreeNode<T> node) {
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
        final boolean[] result = {false};

        traversalInDepth(root, treeNode -> {
            if (treeNode.getData().equals(node.getData())) {
                result[0] = true;
            }
        });

        return result[0];
    }

    // 3. Удаление первого вхождения узла по значению.


    // 4. Получение числа элементов.
    public int getNodesAmount(TreeNode<T> initialNode) {
        final int[] nodesAmount = {0};
        // Реализуем нагрузку в виде подсчета узлов.
        traversalInDepth(initialNode, treeNode -> nodesAmount[0]++);

        return nodesAmount[0];
    }

    // 5. Обходы.
    // 5.1. В глубину - это Стек, в Ширину - это Очередь.
    public void printInDepth() {
        traversalInDepth(root, treeNode -> System.out.println(treeNode.getData()));
    }

    private void traversalInDepth(TreeNode<T> node, Consumer<TreeNode<T>> consumer) {
        Stack<TreeNode<T>> treeNodeStack = new Stack<>();
        treeNodeStack.push(node);

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
        traversalInWide(root, treeNode -> System.out.println(treeNode.getData()));
    }

    private void traversalInWide(TreeNode<T> node, Consumer<TreeNode<T>> consumer) {
        Deque<TreeNode<T>> treeNodeQueue = new ArrayDeque<>();
        treeNodeQueue.add(node);

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

    // 6. Печать дерева в консоль.


    // Раз мы не можем передать в функцию другую функцию как аргумент
    // - только объекты можем передать.
    // Тогда передадим экземпляр printLoad функционального интерфейса Consumer,
    // переопределив его единственный метод.

    /* Еще не доделано.
    public void delete(TreeNode<T> node) {
        TreeNode<T> parentNode = root;
        TreeNode<T> currentNode = root;

        TreeNode<T> deletedNode;
        TreeNode<T> deletedNodeParent;
        TreeNode<T> minLeftNode;
        TreeNode<T> minLeftNodeParent;

        boolean currentNodeIsLeft = true;

        while (true) {
            if (node.getData().compareTo(currentNode.getData()) == 0) {
                // 1. Лист. Нет детей.
                if (currentNode.getLeft() == null && currentNode.getRight() == null) {
                    // Найденный узел правый или левый ребенок?
                    if (currentNodeIsLeft) {
                        parentNode.setLeft(null);
                    } else {
                        parentNode.setRight(null);
                    }

                    return;
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

                }
            }

            // Поиск узла.
            // Идем влево (узел меньше текущего) или вправо.
            if (node.getData().compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getLeft();
                    currentNodeIsLeft = true;
                } else {
                    return;
                }
            } else {
                if (currentNode.getRight() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getRight();
                    currentNodeIsLeft = false;
                } else {
                    return;
                }
            }
        }
    }
*/

    /* Это эксперимент.
 TODO вставить └ символ. Не корректно работает если удалить Аллу.
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
*/
}