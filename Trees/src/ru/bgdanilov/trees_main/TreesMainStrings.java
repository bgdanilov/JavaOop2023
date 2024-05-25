package ru.bgdanilov.trees_main;

import ru.bgdanilov.trees.BinaryTree;
import ru.bgdanilov.trees.TreeNode;

// TODO Доделать toString чтобы понятно было где чьи потомки.
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

        stringsTree.setRoot(name1);
        stringsTree.add(name2);
        stringsTree.add(name3);
        stringsTree.add(name4);
        stringsTree.add(name5);
        stringsTree.add(name6);
        stringsTree.add(name7);

        System.out.println(stringsTree);
    }
}