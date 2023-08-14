package ru.bgdanilov.array_list_home;

import java.util.ArrayList;

public class GetArrayListWithoutItemsRepetition {
    public static void main(String[] args) {
        // 3. Есть список из целых чисел, в нём некоторые числа могут повторяться.
        // Надо создать новый список, в котором будут элементы первого списка в таком же порядке,
        // но без повторений.
        ArrayList<Integer> inputIntegers = new ArrayList<>();

        inputIntegers.add(1);
        inputIntegers.add(1);
        inputIntegers.add(5);
        inputIntegers.add(2);
        inputIntegers.add(1);
        inputIntegers.add(3);
        inputIntegers.add(3);
        inputIntegers.add(5);

        // Создадим новый список - копию и его будем изменять.
        // По-старинке, циклами.
        ArrayList<Integer> outputIntegers = new ArrayList<>(inputIntegers);
        int size = outputIntegers.size();

        for (int i = 0; i < size; i++) {
            int temp = outputIntegers.get(i);

            // Ищем одинаковые элементы с temp и удаляем.
            for (int j = i + 1; j < size; j++) {
                if (outputIntegers.get(j) == temp) {
                    outputIntegers.remove(j);
                }
            }
        }

        System.out.println(outputIntegers);
    }
}