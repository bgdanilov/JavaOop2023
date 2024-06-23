package ru.bgdanilov.trees_main;

import ru.bgdanilov.trees.BinaryTree;
import ru.bgdanilov.trees.Building;

public class TreesMainBuildings {
    public static void main(String[] args) {
        BinaryTree<Building> cityTree = new BinaryTree<>(); // дерево;

        Building home = new Building("apt", 2);
        Building hotel = new Building("hotel", 20);
        Building service = new Building("service", 3);
        Building theatre = new Building("theatre", 1);

        // 1. Добавление в Дерево.
        System.out.println("1. Добавление в Дерево.");
        cityTree.add(home);
        cityTree.add(hotel);
        cityTree.add(service);
        cityTree.add(theatre);

        System.out.println("Выведем корень:");
        System.out.println(cityTree.getRoot().getData());
        System.out.println("----");

        // 2. Поиск узла.
        System.out.println("2. Есть ли home?");
        System.out.println(cityTree.contains(home));
        System.out.println("----");

        // 3. Удаление первого вхождения узла по значению.
        System.out.println("3. Удаление первого вхождения узла по значению.");
        System.out.println("Не реализовано.");
        System.out.println("----");

        // 4. Получение числа элементов.
        System.out.println("4. Получение числа элементов.");
        System.out.println("Число узлов равно: " + cityTree.getNodesAmount());

        // 5. Обходы в ширину и в глубину.
        System.out.println("5.1. Обход в ширину.");
        cityTree.printInWide();
        System.out.println("----");

        System.out.println("5.2. Обход в глубину.");
        cityTree.printInDepth();
        System.out.println("----");
    }
}