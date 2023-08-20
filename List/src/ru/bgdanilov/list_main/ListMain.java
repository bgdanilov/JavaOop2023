package ru.bgdanilov.list_main;

import ru.bgdanilov.list.List;

public class ListMain {
    public static void main(String[] args) {
        // Создаем список - на самом деле объект с полем, которое хранит ссылку на
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

        System.out.println("Наш список есть: " + strings);
        System.out.println("=============================");

        // 1.1. Получение размера списка.
        System.out.println("1.1. Длина нашего списка равна: " + strings.getLength() + ".");
        System.out.println("1.1. Расчет длины нашего списка: " + strings.countLength() + ".");
        System.out.println();

        // 1.2. Получение значения первого элемента.
        System.out.println("1.2. Первый элемент списка: " + strings.getFirstItem() + ".");
        System.out.println();

        // 1.3. Получение/изменение значения по указанному индексу.
        // Получение.
        System.out.println("Получение:");
        System.out.println("1.3. Элемент списка с индексом 0: " + strings.getItemByIndex(0) + ".");
        System.out.println("1.3. Элемент списка с индексом 3: " + strings.getItemByIndex(3) + ".");
        System.out.println("----------------");
        System.out.println("Изменение:");
        System.out.println("1.3. Поменяли элемент: " + strings.changeItemByIndex(1, "Два") + ".");

        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println();

        // 1.4. Удаление элемента по индексу, пусть выдает значение элемента.
        System.out.println("1.4. Удаляем элемент с индексом 2: " + strings.deleteItemByIndex(2) + ".");
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println("1.4. Удаляем элемент с индексом 0: " + strings.deleteItemByIndex(0) + ".");
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println("1.4. Удаляем элемент с индексом 1: " + strings.deleteItemByIndex(1) + ".");
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println("1.4. Удаляем элемент с индексом 0: " + strings.deleteItemByIndex(0) + ".");
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");

        // Проверим длину списка.
        System.out.println("Длина нашего списка равна: " + strings.getLength() + ".");
        System.out.println("Расчет длины нашего списка: " + strings.countLength() + ".");
        System.out.println();

        // 1.5. Вставка элемента в начало.
        // Проверим вставку в пустой список.
        System.out.println("1.5. Вставляем в начало новый элемент \"Два\".");
        strings.addFirst("Два");
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        // Проверим вставку в НЕ пустой список.
        System.out.println("1.5. Вставляем в начало новый элемент \"Один\".");
        strings.addFirst("Один");
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println();

        // 1.6. Вставка элемента по индексу.
        System.out.println("1.6. Вставляем в конец новый элемент \"Четыре\".");
        strings.addItemByIndex(2, "Четыре");
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println("1.6. Вставляем новый элемент \"Ноль\" индексом 0.");
        strings.addItemByIndex(0, "Ноль");
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println("1.6. Вставляем новый элемент \"Три\" индексом 3.");
        strings.addItemByIndex(3, "Три");
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println();

        // 1.7. Удаление узла по значению, пусть выдает true, если элемент был удален.
        System.out.println("Удаляем элемент \"Ноль\". Удалили? - " + strings.deleteByData("Ноль"));
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println("Удаляем элемент \"Четыре\". Удалили? - " + strings.deleteByData("Четыре"));
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println("Удаляем элемент \"Два\". Удалили? - " + strings.deleteByData("Два"));
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println("Удаляем элемент \"Три\". Удалили? - " + strings.deleteByData("Три"));
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println("Удаляем элемент \"Один\". Удалили? - " + strings.deleteByData("Один"));
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println();

        // Восстановим список.
        System.out.println("Восстановим список.");
        strings.addFirst("One");
        strings.addItemByIndex(1, "Two");
        strings.addItemByIndex(2, "Three");
        strings.addItem("Four");
        strings.addItemByIndex(0, "Zero");
        System.out.println("Наш список теперь есть: " + strings);
        // Проверим длину списка.
        System.out.println("Длина нашего списка равна: " + strings.getLength() + ".");
        System.out.println("Расчет длины нашего списка: " + strings.countLength() + ".");
        System.out.println("=============================");
        System.out.println();

        // 1.8. Удаление первого элемента, пусть выдает значение элемента.
        System.out.println("Удаляем первый элемент: " + strings.deteteFirst());
        System.out.println("Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println();

        //  1.9. Разворот списка за линейное время.
        strings.reverse();
        System.out.println("Разворачиваем список. Наш список теперь есть: " + strings);
        System.out.println("=============================");
        System.out.println();

        // 1.10. Копирование списка.
        List<String> nullst = new List<>();

        System.out.println("1.10. Копирование списка.");
        List<String> stringsCopy = strings.copyList();
        stringsCopy.reverse(); // Развернем обратно копию.
        System.out.println("Наш исходный список: " + strings);
        System.out.println("Развернутая копия исходного списка: " + stringsCopy);
        System.out.println("=============================");
    }
}

/**
 * Вопросы:
 * 1. Можно ли Дженерик в printf засунуть?
 */