package ru.bgdanilov.trees_main;

import ru.bgdanilov.trees.BinaryTree;
import ru.bgdanilov.trees.Building;
import ru.bgdanilov.trees.TreeNode;

public class TreesMainBuildings {
    public static void main(String[] args) {
        BinaryTree<Building> cityTree = new BinaryTree<>(); // дерево;

        TreeNode<Building> hotel = new TreeNode<>(new Building("Hilton INN",11)); // узел дерева типа здание;
        TreeNode<Building> shop = new TreeNode<>(new Building("FIT Service", 4));
        TreeNode<Building> cafe = new TreeNode<>(new Building("Best Coffee", 1));
        TreeNode<Building> hospital = new TreeNode<>(new Building("Central Hospital", 113));

        TreeNode<Building> westHospital = new TreeNode<>(new Building("West Hospital", 113));

        cityTree.setRoot(hotel);
        cityTree.add(shop);
        cityTree.add(cafe);
        cityTree.add(hospital);

        System.out.println(cityTree.contains(westHospital));
        cityTree.printInDepth();
    }
}