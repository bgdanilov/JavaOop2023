package ru.bgdanilov.array_list_main;
import ru.bgdanilov.array_list.ArrayListCustom;

import java.util.Arrays;

public class ArrayListMain {
    public static void main(String[] args) {
        // Создаем список по-умолчанию - массив с длинной 10.
        ArrayListCustom<String> strings = new ArrayListCustom<>();

        System.out.println("Размер списка: " + strings.size());
        System.out.println("Пуст ли список? " + strings.isEmpty());
        System.out.println();

        // Добавляем элемен в конец.
        System.out.println("Добавляем элемен в конец: " + strings.add("zero"));
        System.out.println("Размер списка: " + strings.size());
        System.out.println("Вместимость списка: " + strings.getCapacity());
        System.out.println("Пуст ли список? " + strings.isEmpty());
        System.out.println(strings);
        System.out.println();

        strings.add(1, "Три");
        System.out.println(strings);
        System.out.println();
    }
}