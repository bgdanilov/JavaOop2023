package ru.bgdanilov.array_list_main;


import ru.bgdanilov.array_list.ArrayListCustom;

import java.util.Arrays;

public class ArrayListMain {
    public static void main(String[] args) {
        ArrayListCustom<String> strings = new ArrayListCustom<>(10);

        System.out.println(strings.size());
        System.out.println(strings.isEmpty());

        strings.add(0, "zero");
    }
}