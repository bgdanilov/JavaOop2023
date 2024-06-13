package ru.bgdanilov.trees_main;

import ru.bgdanilov.trees.BinaryTree;
import ru.bgdanilov.trees.TreeNode;

public class TreesMainStrings {
    public static void main(String[] args) {
        // 1. Добавление в Дерево.
        BinaryTree<String> stringsTree = new BinaryTree<>(new TreeNode<>("Boris"));
        stringsTree.add("Alex");
        stringsTree.add("Yana");
        stringsTree.add("Ivan");
        stringsTree.add("Roma");
        stringsTree.add("Dima");
        stringsTree.add("Alla");
        stringsTree.add("Cima");
        stringsTree.add("Zohan");
        stringsTree.add("Zaur");
        System.out.println("----");

        // 2. Поиск узла.
        System.out.println("2. Есть ли узел с именем Zohan?");
        System.out.println(stringsTree.contains("Zohan"));
        System.out.println("----");

        // 4. Получение числа элементов.
        System.out.println("4. Получение числа элементов.");
        System.out.println("Число узлов равно: " + stringsTree.getNodesAmount());

        // 5. Обходы в ширину и в глубину.
        System.out.println("5.1. Обход в ширину.");
        stringsTree.printInWide();
        System.out.println("----");

        System.out.println("5.2. Обход в глубину.");
        stringsTree.printInDepth();
        System.out.println("----");
    }
}