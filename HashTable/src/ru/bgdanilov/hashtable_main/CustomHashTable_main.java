package ru.bgdanilov.hashtable_main;

import ru.bgdanilov.hashtable.CustomHashTable;

import java.util.Arrays;
import java.util.ArrayList;

public class CustomHashTable_main {
    public static void main(String[] args) {
        CustomHashTable<String> hashTable = new CustomHashTable<>(4);

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
        System.out.println("Итого: " + hashTable);
        System.out.println();

        // 5. Создать массив из значений таблицы.
        System.out.println("5. Создать массив из значений таблицы: " + Arrays.toString(hashTable.toArray()));
        System.out.println();

        // 6. Возвращает из значений таблицы массив в переданный массив.
        System.out.println("6. Возвращает из значений таблицы массив в переданный массив.");
        String[] destinationArray = {"4", "5", "6", "7", "8", "9", "10"};
        System.out.println("Указанный массив: " + Arrays.toString(destinationArray));
        System.out.println("Итого: " + Arrays.toString(hashTable.toArray(destinationArray)));
        System.out.println();

        // 8. Удаление элемента из таблицы.
        System.out.println("8. Удаляем объект \"One\" из таблицы.");
        System.out.println("Итого: " + hashTable.remove("One") + ", " + hashTable);
        System.out.println();

        // 9. Содержатся ли элементы указанной коллекции в нашей таблице?
        System.out.println("9. Содержатся ли элементы указанной коллекции в нашей таблице?");
        ArrayList<String> specifiedList = new ArrayList<>();
        specifiedList.add("Boris");
        specifiedList.add("Two");
        //specifiedList.add("два"); // А так - false.

        System.out.println("Указанный список: " + specifiedList);
        System.out.println("Наша таблица: " + hashTable);
        System.out.println(hashTable.containsAll(specifiedList));
        System.out.println();

        // 10. Добавляет все элементы из переданной коллекции в таблицу.
        System.out.println("10. Добавляет все элементы из переданной коллекции в таблицу.");
        specifiedList.clear();
        specifiedList.add("Kate");
        specifiedList.add("Boris");
        specifiedList.add("Jane");
        specifiedList.add("Bob");
        specifiedList.add("Ivan");
        System.out.println("Указанный список: " + specifiedList);
        System.out.println("Наша таблица: " + hashTable);
        System.out.println("Итого: " + hashTable.addAll(specifiedList) + ", " + hashTable);
        System.out.println();

        // 11. Удаляет из таблицы все элементы, содержащиеся в указанной коллекции.
        System.out.println("11. Удаляет из этого списка все его элементы, содержащиеся в указанной коллекции.");
        System.out.println("Указанный список: " + specifiedList);
        System.out.println("Наша таблица: " + hashTable);
        System.out.println("Итого: " + hashTable.removeAll(specifiedList) + ", " + hashTable);
        System.out.println();

        // 12. Сохраняет только те элементы в этом списке, которые содержатся в указанной коллекции.
        System.out.println("12. Сохраняет только те элементы в этом списке, которые содержатся в указанной коллекции.");
        specifiedList.clear();
        specifiedList.add("22");
        specifiedList.add("Two");
        specifiedList.add("33");
        specifiedList.add("44");
        specifiedList.add("55");
        specifiedList.add("Bob");
        specifiedList.add("Jane");
        specifiedList.add("Ivan");
        specifiedList.add("Kate");
        specifiedList.add("Boris");

        System.out.println("Указанный список: " + specifiedList);
        System.out.println("Наша таблица: " + hashTable);
        System.out.println("Итого: " + hashTable.retainAll(specifiedList) + ", " + hashTable);
        System.out.println();

        // 13. Удаляем все элементы из таблицы.
        System.out.println("13. Удаляем все элементы из таблицы.");
        System.out.println("Наш список: " + hashTable);
        hashTable.clear();
        System.out.println("Итого: " + hashTable);
        System.out.println();

        // Создадим и выведем таблицу по-умолчанию.
        CustomHashTable<String> defaultHashTable = new CustomHashTable<>();
        defaultHashTable.addAll(specifiedList);
        System.out.println("Создадим и выведем таблицу по-умолчанию: " + defaultHashTable);
    }
}