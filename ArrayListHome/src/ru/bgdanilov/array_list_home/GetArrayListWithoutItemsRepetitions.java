package ru.bgdanilov.array_list_home;

import java.util.ArrayList;
import java.util.Arrays;

public class GetArrayListWithoutItemsRepetitions {
    public static void main(String[] args) {
        // 3. Есть список из целых чисел, в нём некоторые числа могут повторяться.
        // Надо создать новый список, в котором будут элементы первого списка в таком же порядке,
        // но без повторений.
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 1, 5, 2, 1, 3, 3, 5, 1, 6));
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("a", "a", "b", "b", "c", "a", "b"));

        System.out.println("Исходный список: " + integers);
        System.out.println("Список без повторения элементов: " + getListWithoutItemsRepetitions(integers));
        System.out.println("Исходный список: " + strings);
        System.out.println("Список без повторения элементов: " + getListWithoutItemsRepetitions(strings));
    }

    public static <E> ArrayList<E> getListWithoutItemsRepetitions(ArrayList<E> list) {
        ArrayList<E> integersWithoutItemsRepetitions = null;
        if (!list.isEmpty()) {
            integersWithoutItemsRepetitions = new ArrayList<>(list.size());
            for (E item : list) {
                if (!integersWithoutItemsRepetitions.contains(item)) {
                    integersWithoutItemsRepetitions.add(item);
                }
            }

            integersWithoutItemsRepetitions.trimToSize();
        }

        return integersWithoutItemsRepetitions;
    }
}

/*
    Вопросы, заметки:
    1. Сделал с Дженериком.
 */