package ru.bgdanilov.array_list_home;

import java.util.ArrayList;
import java.util.Iterator;

// Задача «ArrayListHome».
// 2. Есть список из целых чисел. Удалить из него все четные числа.
// В этой задаче новый список создавать нельзя.
// Рассмотрены три случая реализации задачи.
// Дальше будем создавать новый список - копию исходного для каждого случая,
// чтобы не было повторного использования исходного списка.
public class DeleteEvenNumbers {
    public static void main(String[] args) {
        // Создаем список.
        ArrayList<Integer> integers = new ArrayList<>();

        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);

        // 2.1. Сначала IDE ругалась, что "Suspicious 'List.remove()' in loop"
        // Просила Итератор - сделал, см. 2.2.
        ArrayList<Integer> integers1 = new ArrayList<>(integers);

        for (int i = 0; i < integers1.size(); i++) {
            int item = integers1.get(i);

            if (item % 2 == 0) {
                integers1.remove(i);
            }

        }

        System.out.println(integers1);

        // 2.2. Теперь IDE ругается, что "The loop can be replaced with 'Collection.removeIf"
        // В итоге получилось 2.3.
        ArrayList<Integer> integers2 = new ArrayList<>(integers);

        Iterator<Integer> iterator = integers2.iterator();

        while (iterator.hasNext()) {
            if (iterator.next() % 2 == 0) {
                iterator.remove();
            }
        }

        System.out.println(integers2);

        // 2.3. В итоге все свелось к одной строчке.
        integers.removeIf(integer -> integer % 2 == 0);
        System.out.println(integers);
    }
}