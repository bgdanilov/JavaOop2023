package ru.bgdanilov.array_list_home;

import java.util.ArrayList;
import java.util.Iterator;

// Задача «ArrayListHome».
// 2. Есть список из целых чисел. Удалить из него все четные числа.
// В этой задаче новый список создавать нельзя.
public class DeleteEvenNumbers {
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();

        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);

        // 2.3.
        integers.removeIf(integer -> integer % 2 == 0);

        System.out.println(integers);

        // 2.1. Сначала IDE ругалась, что "Suspicious 'List.remove()' in loop"
        // Просила Итератор - сделали, см. 2.2.
        /*
        for (int i = 0; i < integers.size(); i++) {
                int element = integers.get(i);

                if (element % 2 == 0) {
                    integers.remove(i);
                }

        }
        */

        // 2.2. Теперь IDE ругается, что "The loop can be replaced with 'Collection.removeIf"
        // В итоге получилось 2.3.
        /*
        Iterator<Integer> iterator = integers.iterator();

        while (iterator.hasNext()) {
            if(iterator.next() % 2 == 0) {
                iterator.remove();
            }
        }
        */
    }
}