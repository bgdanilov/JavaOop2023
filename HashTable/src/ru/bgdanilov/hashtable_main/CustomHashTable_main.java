package ru.bgdanilov.hashtable_main;

import ru.bgdanilov.hashtable.CustomHashTable;

import java.util.Arrays;
import java.util.ArrayList;

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
        hashTable.add("22");
        hashTable.add("33");
        hashTable.add("44");
        hashTable.add("55");
        System.out.println("   - есть ли элемент \"One\" в таблице? " + hashTable.contains("One"));
        //hashTable.add("Boris", 4);
        System.out.println(hashTable);
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
        System.out.println("8. Удаляем объект \"One\" из таблицы: " + hashTable.remove("One"));
        System.out.println(hashTable);
        System.out.println();

        // 9. Содержатся ли элементы указанной коллекции в нашей таблице?
        System.out.println("9. Содержатся ли элементы указанной коллекции в нашей таблице?");

        ArrayList<String> specifiedList = new ArrayList<>();
        specifiedList.add("Boris");
        specifiedList.add("Two");
        //specifiedList.add("два"); // А так - false.

        System.out.println("Указанный список: " + specifiedList);
        System.out.println("Наш список: " + hashTable);
        // Почему тут warning?
        System.out.println(hashTable.containsAll(specifiedList));
        System.out.println();

        // 10. Добавляет все элементы из указанной коллекции в таблицу.
        System.out.println("10. Добавляет все элементы из указанной коллекции в конец этого списка.");
        ArrayList<String> specifiedList2 = new ArrayList<>();
        specifiedList2.add("Kate");
        specifiedList2.add("Boris");
        specifiedList2.add("Jane");
        specifiedList2.add("Bob");
        specifiedList2.add("Ivan");
        System.out.println("Указанный список: " + specifiedList2);
        System.out.println("Наш список: " + hashTable);
        System.out.println(hashTable.addAll(specifiedList2));
        System.out.println("Итого: " + hashTable);
        System.out.println();

        // 11. Удаляет из этого списка все его элементы, содержащиеся в указанной коллекции.
        System.out.println("11. Удаляет из этого списка все его элементы, содержащиеся в указанной коллекции.");
        System.out.println("Указанный список: " + specifiedList2);
        System.out.println("Наш список: " + hashTable);
        System.out.println(hashTable.removeAll(specifiedList2));
        System.out.println("Итого: " + hashTable);
        System.out.println();

        System.out.println(Arrays.toString(hashTable.toArray()));

        // 12. Сохраняет только те элементы в этом списке, которые содержатся в указанной коллекции.
        System.out.println("12. Сохраняет только те элементы в этом списке, которые содержатся в указанной коллекции.");
        specifiedList.clear();
        hashTable.addAll(specifiedList2);
        specifiedList.add("1");
        specifiedList.add("Two");
        specifiedList.add("3");
        specifiedList.add("4");
        specifiedList.add("5");
        specifiedList.add("6");

        System.out.println("Указанный список: " + specifiedList);
        System.out.println("Наш список: " + hashTable);
        System.out.println(hashTable.retainAll(specifiedList));
        System.out.println("Итого: " + hashTable);
        System.out.println();

        // 13. Удаляем все элементы из таблицы.
        System.out.println("13. Удаляем все элементы из таблицы.");
        hashTable.clear();
        System.out.println(hashTable);
        System.out.println();
    }
}
