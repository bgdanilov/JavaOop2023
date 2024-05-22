package ru.bgdanilov.trees_main;

import ru.bgdanilov.trees.BinaryTree;
import ru.bgdanilov.trees.TreeNode;

public class TreesMain {
    public static void main(String[] args) {
        BinaryTree<String> myTree = new BinaryTree<>();
        myTree.setRoot(new TreeNode<>("Boris"));

        System.out.println(myTree.getRoot().getData());
    }
}
