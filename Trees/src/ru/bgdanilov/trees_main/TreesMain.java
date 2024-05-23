package ru.bgdanilov.trees_main;

import ru.bgdanilov.trees.BinaryTree;
import ru.bgdanilov.trees.Building;
import ru.bgdanilov.trees.TreeNode;

import java.util.TreeMap;

public class TreesMain {
    public static void main(String[] args) {
        BinaryTree<Building> city = new BinaryTree<>(); // дерево;
        TreeNode<Building> building = new TreeNode<>(); // узел дерева типа здание, но пустой;
        city.setRoot(building); // помещаем пустой узел в корень дерева;

        Building hotel = new Building(222); // отель на 222 комнаты;
        building.setData(hotel); // помещаем Отель в узел-корень;


        // city.setRoot(new TreeNode<>(new Building(222))); // или так можно было бы;

        // Пытаемся сравнить какой отель больше.
        System.out.println(city.getComparisonValue(new Building(111)));
    }
}
