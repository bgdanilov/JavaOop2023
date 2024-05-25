package ru.bgdanilov.trees_main;

import ru.bgdanilov.trees.BinaryTree;
import ru.bgdanilov.trees.Building;
import ru.bgdanilov.trees.TreeNode;

// TODO Тут что-то неправильно. Надо еще обдумать.

public class TreesMainBuildings {
    public static void main(String[] args) {
        BinaryTree<Building> cityTree = new BinaryTree<>(); // дерево;

        TreeNode<Building> hotel = new TreeNode<>(new Building(222)); // узел дерева типа здание;
        TreeNode<Building> shop = new TreeNode<>(new Building(11));
        TreeNode<Building> cafe = new TreeNode<>(new Building(2));
        TreeNode<Building> hospital = new TreeNode<>(new Building(1134));

        cityTree.setRoot(hotel);
        cityTree.add(shop);
        cityTree.add(cafe);
        cityTree.add(hospital);

        System.out.println(cityTree);
    }
}