package ru.bgdanilov.list_main;

import ru.bgdanilov.list.List;

import java.util.Arrays;

public class ListMain {
    public static void main(String[] args) {
        // Создаем типа список - на самом деле объект с полем, которое хранит ссылку на
        // объект специального класса ListItem, который мы считаем головой.
        // По-умолчанию, при инициализации - эта ссылка = null. Список пустой.
        List<String> strings = new List<>();
        // Далее, мы добавляем элемент в список, а на самом деле:
        // 1. Создаем объект списка.
        // 2. Присваиваем адрес объекта полю head списка.
        // 3. Добавляем другие элементы, просто  просто записывая в next.
        strings.addItem("One");
        strings.addItem("Two");
        strings.addItem("Three");
        strings.addItem("Four");

        System.out.println(strings);

        System.out.println(strings.getSize());
        System.out.println(strings.getItemByIndex(3));

        // Как сюда добавить, чтобы String из <T> бралось?
        String oldListItem = strings.change2ItemByIndex(2, "Три");
        System.out.println("Старое значение было: " + oldListItem);
        System.out.println(strings);

        List<String> sss = new List<>();
        System.out.println(sss);
        System.out.println(sss.getSize());

        System.out.println(strings.countSize());
        System.out.println(strings.deleteByIndex(1));
        System.out.println(strings);

        System.out.println(strings.countSize());

        strings.addFirst("Zero");
        System.out.println(strings);


        int[] array = new int[] {};
        System.out.println(Arrays.toString(array));
        System.out.println(array.length); // 0
        //System.out.println(array[0]); // e


        List<Integer> strings2 = new List<>();
        strings2.addItemByIndex(0, 1);
        strings2.addItemByIndex(1, 2);
        strings2.addItemByIndex(2, 3);
        strings2.addItemByIndex(3, 4);

        System.out.println(strings2);
        System.out.println(strings2.getSize());
        System.out.println(strings2.countSize());

        System.out.println(strings2.deleteByData(33));
        System.out.println(strings2);

        strings2.reverse();
        System.out.println(strings2);


    }
}
