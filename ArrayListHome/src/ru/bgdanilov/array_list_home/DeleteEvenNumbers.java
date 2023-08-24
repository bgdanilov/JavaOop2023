package ru.bgdanilov.array_list_home;

import java.util.ArrayList;
import java.util.Arrays;
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
        ArrayList<Integer> integers1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        // 2.1. Сначала IDE ругалась, что "Suspicious 'List.remove()' in loop"
        // Просила Итератор - сделал, см. 2.2.
        ArrayList<Integer> integers2 = new ArrayList<>(integers1);
        System.out.println("2.1. Удаление в цикле. Исходный массив: " + integers2);

        for (int i = 0; i < integers2.size(); i++) {
            if (integers2.get(i) % 2 == 0) {
                integers2.remove(i);
                i--; // компенсируем сдвиг элементов влево.
            }

            //integers2.trimToSize();
        }

        System.out.println("Массив без четных чисел: " + integers2);
        System.out.println("Размер массива без четных чисел: " + integers2.size());
        System.out.println();

        // 2.2. Теперь IDE ругается, что "The loop can be replaced with 'Collection.removeIf"
        // В итоге получилось 2.3.
        ArrayList<Integer> integers3 = new ArrayList<>(integers1);
        System.out.println("2.2. Удаление итератором. Исходный массив: " + integers3);

        Iterator<Integer> iterator = integers3.iterator();

        while (iterator.hasNext()) {
            if (iterator.next() % 2 == 0) {
                iterator.remove();
            }
        }

        System.out.println("Массив без четных чисел: " + integers3);
        System.out.println("Размер массива без четных чисел: " + integers3.size());
        System.out.println();

        // 2.3. В итоге все свелось к одной строчке.
        System.out.println("2.3. Удаление пока не изученным кодом по совету IDE.");
        System.out.println("Исходный массив: " + integers1);
        integers1.removeIf(integer -> integer % 2 == 0);
        System.out.println("Массив без четных чисел: " + integers1);
        System.out.println("Размер массива без четных чисел: " + integers1.size());
    }
}

/*
    Вопросы:
    1. В п. 2.1. добавил поправку на сдвиг элементов влево, но ведь и без нее работало?
    И размер списка правильный выдает без коррекции размера integers2.trimToSize() ?
    2. В п. 2.2. Ворнинг, я так понимаю, предлагающий более элегантное решение.
    Можно удалить этот блок вообще?
 */