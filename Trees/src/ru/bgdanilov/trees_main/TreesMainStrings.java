package ru.bgdanilov.trees_main;

import ru.bgdanilov.trees.BinaryTree;
import ru.bgdanilov.trees.TreeNode;

import java.util.function.Consumer;

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
        TreeNode<String> name8 = new TreeNode<>("Cima");
        TreeNode<String> name9 = new TreeNode<>("Zohan");
        TreeNode<String> name10 = new TreeNode<>("Zaur");
        //TreeNode<String> name11 = new TreeNode<>("Abba");

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

        // Раз мы не можем передать в функцию другую функцию как аргумент
        // - только объекты можем передать.
        // Тогда передадим экземпляр printLoad функционального интерфейса Consumer,
        // переопределив его единственный метод.

        // Почему нужно именно String писать? А не <T> как в общем виде.
        Consumer<TreeNode<String>> printLoad = new Consumer<>() {
            @Override
            public void accept(TreeNode treeNode) {
                System.out.println(treeNode.getData());
            }
        };

        final int[] nodesAmount = {0};
        Consumer<TreeNode<String>> countLoad = treeNode -> nodesAmount[0]++;

        stringsTree.PrintDeep(name1, printLoad);
        System.out.println("----");

        stringsTree.PrintDeep(name1, countLoad);
        System.out.println("Число узлов равно: " + nodesAmount[0]);
    }
}