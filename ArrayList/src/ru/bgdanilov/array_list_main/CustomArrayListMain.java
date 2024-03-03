package ru.bgdanilov.array_list_main;

import ru.bgdanilov.array_list.CustomArrayList;

import java.util.Arrays;

public class CustomArrayListMain {
    public static void main(String[] args) {
        try {
            // Создаем список по-умолчанию - массив с длинной 10.
            CustomArrayList<String> strings = new CustomArrayList<>();
            System.out.println(strings);

            // 1. Возвращает количество элементов списка.
            System.out.println("1. Размер списка: " + strings.size());
            System.out.println();

            // 2. Пуст ли список?
            System.out.println("2. Пуст ли список? " + strings.isEmpty());
            System.out.println();

            // 7. Добавляем элемент в конец.
            System.out.println("7. Добавляем элемент zero в конец: " + strings.add("zero"));
            System.out.println("Размер списка: " + strings.size());
            System.out.println("Вместимость списка: " + strings.getCapacity());
            System.out.println("Пуст ли список? " + strings.isEmpty());
            System.out.println("Наш список: " + strings);
            System.out.println();

            // Подгоняем вместимость списка под его размер.
            System.out.println("Подгоняем вместимость списка под его размер:");
            strings.trimToSize();
            System.out.println("Наш список: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println("Вместимость списка: " + strings.getCapacity());
            System.out.println();

            System.out.println("Добавляем еще элементы.");
            strings.add("два");
            strings.add("три");
            System.out.println("Наш список: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println("Вместимость списка: " + strings.getCapacity());
            System.out.println();

            // 3. Содержится ли указанный элемент в списке?
            System.out.println("3. Содержится ли элемент \"Два\" в списке?");
            System.out.println(strings.contains("Два"));
            System.out.println();

            // 4. Итератор. Не надо делать?

            // 5. Преобразует список в массив.
            System.out.println("5. Преобразует список в массив.");
            System.out.println("Получен массив: " + Arrays.toString(strings.toArray()));
            System.out.println();

            // 6. Возвращает из списка массив в указанный массив.
            String[] destinationArray = {"4", "5", "6", "7", "8", "9", "10"};
            System.out.println("6. Возвращает из списка массив в указанный массив.");
            System.out.println("Указанный массив: " + Arrays.toString(destinationArray));
            System.out.println("Итого: " + Arrays.toString(strings.toArray(destinationArray)));
            System.out.println();

            // 8. Удаляет первый попавшийся указанный элемент, если он есть в списке.
            System.out.println("8. Удаляет первый попавшийся указанный элемент, если он есть в списке.");
            System.out.println("Удаляем \"три\": " + strings.remove("три"));
            System.out.println();
            System.out.println(strings);
            System.out.println();

            // 9. Содержатся ли элементы указанного списка в нашем списке?
            System.out.println("9. Содержатся ли элементы указанного списка в нашем списке?");

            CustomArrayList<String> specifiedList = new CustomArrayList<>();
            specifiedList.add("zero");
            specifiedList.add("Два");
            //specifiedList.add("два"); // А так - true.
            System.out.println("Указанный список: " + specifiedList);
            System.out.println("Наш список: " + strings);

            // Почему тут warning?
            System.out.println(specifiedList.containsAll(strings));
            System.out.println();

            // 10. Добавляет все элементы из указанной коллекции в конец этого списка.
            System.out.println("10. Добавляет все элементы из указанной коллекции в конец этого списка.");
            CustomArrayList<String> destinationList = new CustomArrayList<>();
            destinationList.add("1");
            destinationList.add("2");
            destinationList.add("3");
            destinationList.add("4");
            destinationList.add("5");
            System.out.println("Указанный список: " + destinationList);
            System.out.println("Наш список: " + strings);
            System.out.println(strings.addAll(destinationList));
            System.out.println("Итого: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println("Вместимость списка: " + strings.getCapacity());
            System.out.println();

            // 11. Вставляет все элементы из указанной коллекции в этот список, начиная с указанной позиции.
            System.out.println("11. Вставляет все элементы из указанной коллекции в этот список, начиная с указанной позиции (3).");
            System.out.println("Указанный список: " + destinationList);
            System.out.println("Наш список: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println("Вместимость списка: " + strings.getCapacity());
            strings.addAll(3, destinationList);
            System.out.println("Итого: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println("Вместимость списка: " + strings.getCapacity());

            System.out.println();

            // 12. Удаляет из этого списка все его элементы, содержащиеся в указанной коллекции.
            System.out.println("12. Удаляет из этого списка все его элементы, содержащиеся в указанной коллекции.");
            destinationList.clear();
            destinationList.add("1");
            destinationList.add("2");
            destinationList.add("3");
            destinationList.add("4");
            destinationList.add("5");
            destinationList.add("6");
            System.out.println("Указанный список: " + destinationList);
            System.out.println("Наш список: " + strings);
            System.out.println(strings.removeAll(destinationList));
            System.out.println("Итого: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println("Вместимость списка: " + strings.getCapacity());
            System.out.println();

            // 13. Сохраняет только те элементы в этом списке, которые содержатся в указанной коллекции.
            System.out.println("13. Сохраняет только те элементы в этом списке, которые содержатся в указанной коллекции.");
            destinationList.clear();
            strings.add(0, "два");
            //destinationList.add("1");
            destinationList.add("1");
            destinationList.add("два");
            destinationList.add("3");
            destinationList.add("4");
            destinationList.add("5");
            destinationList.add("6");
            System.out.println("Указанный список: " + destinationList);
            System.out.println("Наш список: " + strings);
            System.out.println(strings.retainAll(destinationList));
            System.out.println("Итого: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println("Вместимость списка: " + strings.getCapacity());
            System.out.println();

            // 14. Удаляет все элементы из списка.
            System.out.println("14. Удаляет все элементы из списка.");
            strings.clear();
            System.out.println("Итого: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println("Вместимость списка: " + strings.getCapacity());
            System.out.println("Пуст ли список? " + strings.isEmpty());
            System.out.println();

            // Добавляем еще элементы.
            System.out.println("Добавляем еще элементы:");
            strings.add(0, "Zero");
            strings.add(1, "One");
            strings.add(2, "Two");
            System.out.println("Наш список: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println();

            // 15. Возвращает элемент списка по индексу.
            System.out.println("15. Возвращает элемент списка по индексу.");
            System.out.println("Элемент с индексом 1: " + strings.get(1));
            System.out.println();

            // 16. Замещает элемент по указанному индексу указанным элементом. Возвращает старое значение.
            System.out.println("16. Замещает элемент по указанному индексу указанным элементом. Возвращает старое значение.");
            System.out.println("Наш список: " + strings);
            System.out.println("Меняем элемент с индексом 1 на \"Один\", старое значение: " + strings.set(1, "Один"));
            System.out.println("Итого: " + strings);
            System.out.println();

            // 17. Вставить элемент по указанному индексу.
            System.out.println("17. Вставить элемент по указанному индексу.");
            System.out.println("Вставляем элемент \"One\" по индексу 1:");
            strings.add(1, "One");
            System.out.println("Итого: " + strings);
            System.out.println();

            // 18. Удалить элемент по указанному индексу.
            System.out.println("18. Удалить элемент по указанному индексу.");
            System.out.println("Наш список: " + strings);
            System.out.println("Удаляем элемент с индексом 3, его значение: " + strings.remove(3));
            System.out.println("Итого: " + strings);
            System.out.println();

            // 19. Возвращает индекс первого вхождения переданного элемента или -1, если элемента в списке нет.
            System.out.println("19. Возвращает индекс первого вхождения переданного элемента или -1, если элемента в списке нет.");
            System.out.println("Наш список: " + strings);
            System.out.println("null ? " + strings.indexOf(null));
            System.out.println("Zero ? " + strings.indexOf("Zero"));
            System.out.println();

            // Добавляем еще элементы.
            System.out.println("Добавляем еще элементы:");
            strings.add(0, "Zero");
            strings.add(3, "Zero");
            strings.add(4, "Zero");
            System.out.println("Наш список: " + strings);
            System.out.println("Размер списка: " + strings.size());
            System.out.println();

            // 20. Возвращает индекс последнего вхождения переданного элемента или -1, если элемента в списке нет.
            System.out.println("20. Индекс последнего вхождения элемента \"Zero\": " + strings.lastIndexOf("Zero"));
            System.out.println();

            // Проверим toString
            System.out.println("Проверим toString");
            CustomArrayList<String> someStrings = new CustomArrayList<>(0);
            //someStrings = null;
            someStrings.add("zero");
            System.out.println("Размер списка: " + someStrings.size());
            System.out.println("Вместимость списка: " + someStrings.getCapacity());
            System.out.println(someStrings);
            System.out.println();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}