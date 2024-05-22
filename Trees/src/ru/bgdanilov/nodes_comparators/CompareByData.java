package ru.bgdanilov.nodes_comparators;

import ru.bgdanilov.trees.TreeNode;

import java.util.Comparator;

public class CompareByData implements Comparator<TreeNode> {
    @Override
    public int compare(TreeNode o1, TreeNode o2) {
        //return o1.getData().compareTo(o2.getData());
        return 0;
    }
}