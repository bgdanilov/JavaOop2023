package ru.bgdanilov.array_list_home;

import java.util.ArrayList;
import java.util.Arrays;

public class GetArrayListWithoutDuplicates {
    public static void main(String[] args) {
        // 3. Есть список из целых чисел, в нём некоторые числа могут повторяться.
        // Надо создать новый список, в котором будут элементы первого списка в таком же порядке,
        // но без повторений.
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 1, 5, 2, 1, 3, 3, 5, 1, 6));
        //ArrayList<Integer> integers = new ArrayList<>(); // пустой список для проверки.
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("a", "a", "b", "b", "c", "a", "b"));

        System.out.println("Исходный список: " + integers);
        System.out.println("Список без повторения элементов: " + getListWithoutDuplicates(integers));
        System.out.println("Исходный список: " + strings);
        System.out.println("Список без повторения элементов: " + getListWithoutDuplicates(strings));
    }

    public static <E> ArrayList<E> getListWithoutDuplicates(ArrayList<E> list) {
        ArrayList<E> itemsWithoutDuplicates = new ArrayList<>();

        if (!list.isEmpty()) {
            for (E item : list) {
                if (!itemsWithoutDuplicates.contains(item)) {
                    itemsWithoutDuplicates.add(item);
                }
            }
        }

        return itemsWithoutDuplicates;
    }
}