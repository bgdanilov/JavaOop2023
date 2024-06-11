package ru.bgdanilov.trees_main;

import ru.bgdanilov.trees.BinaryTree;
import ru.bgdanilov.trees.TreeNode;

public class TreesMainStrings {
    public static void main(String[] args) {
        BinaryTree<String> stringsTree = new BinaryTree<>();

        // Узлы.
        TreeNode<String> name1 = new TreeNode<>("Boris");
        TreeNode<String> name2 = new TreeNode<>("Alex");
        TreeNode<String> name3 = new TreeNode<>("Yana");
        TreeNode<String> name4 = new TreeNode<>("Ivan");
        TreeNode<String> name5 = new TreeNode<>("Roma");
        TreeNode<String> name6 = new TreeNode<>("Dima");
        TreeNode<String> name7 = new TreeNode<>("Alla");
        TreeNode<String> name8 = new TreeNode<>("Cima");
        TreeNode<String> name9 = new TreeNode<>("Zohan");
        TreeNode<String> name10 = new TreeNode<>("Zaur");
        //TreeNode<String> name11 = new TreeNode<>("Abba");
        TreeNode<String> name12 = new TreeNode<>("Zohan");

        // 1. Добавление в Дерево.
        System.out.println("1. Добавление в Дерево. Полученное дерево.");
        stringsTree.setRoot(name1);
        stringsTree.add(name2);
        stringsTree.add(name3);
        stringsTree.add(name4);
        stringsTree.add(name5);
        stringsTree.add(name6);
        stringsTree.add(name7);
        stringsTree.add(name8);
        stringsTree.add(name9);
        stringsTree.add(name10);
        //stringsTree.add(name11);
        stringsTree.printInDepth();
        System.out.println("----");

        // 2. Поиск узла.
        System.out.println("2. Есть ли узел с именем Zohan?");
        System.out.println(stringsTree.contains(name12));
        System.out.println("----");

        // 4. Получение числа элементов.
        System.out.println("4. Получение числа элементов.");
        System.out.println("Число узлов равно: " + stringsTree.getNodesAmount(name1));

        // 5. Обходы в ширину и в глубину.
        System.out.println("5.1. Обход в ширину.");
        stringsTree.printInWide();
        System.out.println("----");

        System.out.println("5.2. Обход в глубину.");
        stringsTree.printInDepth();
        System.out.println("----");
    }
}