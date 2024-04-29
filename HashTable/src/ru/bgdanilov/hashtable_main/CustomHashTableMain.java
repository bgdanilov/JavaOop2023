package ru.bgdanilov.hashtable_main;

import ru.bgdanilov.hashtable.CustomHashTable;

import java.util.*;

public class CustomHashTableMain {
    public static void main(String[] args) {
        try {
            CustomHashTable<String> hashTable = new CustomHashTable<>(4);

            // 1. Получим размер хеш-таблицы.
            System.out.println("1. Получим размер хеш-таблицы: " + hashTable.size());
            System.out.println();

            // 2. Хеш-таблица пуста?
            System.out.println("2. Хеш-таблица пуста? " + hashTable.isEmpty() + ", " + hashTable);
            System.out.println();

            // 3. Есть ли элемент в хеш-таблице.
            System.out.println("3. Есть ли элемент \"One\" в хеш-таблице? " + hashTable.contains("One"));
            System.out.println();

            // 7. Добавить объект в хеш-таблицу.
            System.out.println("7. Добавить объекты \"One\", \"Two\", null, null в хеш-таблицу.");
            hashTable.add("One");
            hashTable.add("Two");
            hashTable.add(null);
            hashTable.add(null);
            System.out.println("   Итого: " + hashTable);
            System.out.println("   - есть ли элемент null в хеш-таблице? " + hashTable.contains(null));
            System.out.println();

            // 5. Создать массив из значений хеш-таблицы.
            System.out.println("5. Создать массив из значений хеш-таблицы: " + Arrays.toString(hashTable.toArray()));
            System.out.println();

            // 6. Возвращает из значений хеш-таблицы массив в переданный массив.
            System.out.println("6. Возвращает из значений хеш-таблицы массив в переданный массив.");
            String[] destinationArray = {"4", "5", "6", "7"};
            System.out.println("   Указанный массив: " + Arrays.toString(destinationArray));
            System.out.println("   Итого: " + Arrays.toString(hashTable.toArray(destinationArray)));
            System.out.println(hashTable.size());
            System.out.println();

            // 8. Удаление элемента из хеш-таблицы.
            System.out.println("8. Удаляем объект null из хеш-таблицы.");
            System.out.println("   Итого: " + hashTable.remove(null) + ", " + hashTable);
            System.out.println(hashTable.size());
            System.out.println();

            // 9. Содержатся ли все элементы указанной коллекции в нашей хеш-таблице?
            System.out.println("9. Содержатся ли все элементы указанной коллекции в нашей хеш-таблице?");
            ArrayList<String> specifiedList = new ArrayList<>();
            specifiedList.add("Boris");
            specifiedList.add("Two");

            System.out.println("   Указанный список: " + specifiedList);
            System.out.println("   Наша хеш-таблица: " + hashTable.containsAll(specifiedList) + ", " + hashTable);
            System.out.println();

            // 10. Добавляет все элементы из переданной коллекции в хеш-таблицу.
            System.out.println("10. Добавляет все элементы из переданной коллекции в хеш-таблицу.");
            specifiedList.clear();
            specifiedList.add("Kate");
            specifiedList.add(null);
            specifiedList.add("Jane");
            specifiedList.add("Bob");
            specifiedList.add("Ivan");
            System.out.println("    Указанный список: " + specifiedList);
            System.out.println("    Наша хеш-таблица: " + hashTable);
            System.out.println("    Итого: " + hashTable.addAll(specifiedList) + ", " + hashTable);
            System.out.println();

            // 11. Удаляет из хеш-таблицы все элементы, содержащиеся в указанной коллекции.
            System.out.println("11. Удаляет из этого списка все его элементы, содержащиеся в указанной коллекции.");
            System.out.println("    Указанный список: " + specifiedList);
            System.out.println("    Наша хеш-таблица: " + hashTable);
            System.out.println("    Итого: " + hashTable.removeAll(specifiedList) + ", " + hashTable);
            System.out.println("    Размер хеш-таблицы: " + hashTable.size());
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

            System.out.println("    Указанный список: " + specifiedList);
            System.out.println("    Наша хеш-таблица: " + hashTable);
            System.out.println("    Итого: " + hashTable.retainAll(specifiedList) + ", " + hashTable);
            System.out.println("    Размер хеш-таблицы: " + hashTable.size());
            System.out.println();

            // 13. Удаляем все элементы из хеш-таблицы.
            System.out.println("13. Удаляем все элементы из хеш-таблицы.");
            System.out.println("    Наш список: " + hashTable);
            hashTable.clear();
            System.out.println("    Итого: " + hashTable);
            System.out.println();

            // Создадим и выведем хеш-таблицу по-умолчанию.
            CustomHashTable<String> defaultHashTable = new CustomHashTable<>();
            defaultHashTable.addAll(specifiedList);
            System.out.println("Создадим и выведем хеш-таблицу по-умолчанию: " + defaultHashTable);
        } catch (ConcurrentModificationException | NoSuchElementException | NullPointerException | IllegalArgumentException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}