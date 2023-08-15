package ru.bgdanilov.list_main;

import ru.bgdanilov.list.List;

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

        System.out.println(strings);

        System.out.println(strings.getCount());

        List<String> sss = new List<>();
        System.out.println(sss);
        System.out.println(sss.getCount());
    }
}
