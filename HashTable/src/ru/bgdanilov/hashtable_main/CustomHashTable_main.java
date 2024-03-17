package ru.bgdanilov.hashtable_main;

import ru.bgdanilov.hashtable.CustomHashTable;

import java.util.Arrays;

public class CustomHashTable_main {
    public static void main(String[] args) {
        CustomHashTable<String> hashTable = new CustomHashTable<>();

        // 1. Получим размер таблицы.
        System.out.println("1. Получим размер таблицы: " + hashTable.size());
        System.out.println();

        // 2. Таблица пуста?
        System.out.println("2. Таблица пуста? " + hashTable.isEmpty() + ", " + hashTable);
        System.out.println();

        // 3. Есть ли элемент в таблице.
        System.out.println("3. Есть ли элемент \"One\" в таблице? " + hashTable.contains("One"));
        System.out.println();

        // 7. Добавить объект в таблицу.
        System.out.println("7. Добавить объекты \"One\" и \"Two\" в таблицу.");
        hashTable.add("One");
        hashTable.add("Two");
        System.out.println("   - есть ли элемент \"One\" в таблице? " + hashTable.contains("One"));
        System.out.println(hashTable);
        System.out.println();

        // 5. Создать массив из значений таблицы.
        System.out.println("5. Создать массив из значений таблицы: " + Arrays.toString(hashTable.toArray()));
        System.out.println();

        // 8. Удаление элемента из таблицы.
        System.out.println("8. Удаляем объект \"One\" из таблицы: " + hashTable.remove("One"));
        System.out.println(hashTable);
        System.out.println();

        // 13. Удаляем все элементы из таблицы.
        System.out.println("13. Удаляем все элементы из таблицы.");
        hashTable.clear();
        System.out.println(hashTable);
        System.out.println();
    }
}
