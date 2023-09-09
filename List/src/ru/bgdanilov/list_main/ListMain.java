package ru.bgdanilov.list_main;

import ru.bgdanilov.list.List;

public class ListMain {
    public static void main(String[] args) {
        try {
            // Создаем список - на самом деле объект с полем, хранящим ссылку на
            // объект специального класса ListItem, который мы считаем головой.
            // По-умолчанию, при инициализации - эта ссылка = null. Список пустой.
            List<String> strings = new List<>();
            // Далее, мы добавляем элемент в список, а на самом деле:
            // 1. Создаем объект списка.
            // 2. Присваиваем адрес объекта полю head списка.
            // 3. Добавляем другие элементы, просто записывая в next.
            strings.add("One");
            strings.add("Two");
            strings.add("Three");
            strings.add("Four");

            System.out.println("Наш список есть: " + strings);
            System.out.println("=============================");

            // 1.1. Получение размера списка.
            System.out.println("1.1. Длина нашего списка равна: " + strings.getLength() + ".");
            System.out.println();

            // 1.2. Получение значения первого элемента.
            System.out.println("1.2. Первый элемент списка: " + strings.getFirst() + ".");
            System.out.println();

            // 1.3. Получение/изменение значения по указанному индексу.
            // Получение.
            System.out.println("Получение:");
            System.out.println("1.3. Элемент списка с индексом 0: " + strings.getByIndex(0) + ".");
            System.out.println("1.3. Элемент списка с индексом 3: " + strings.getByIndex(3) + ".");
            System.out.println("----------------");
            System.out.println("Изменение:");
            System.out.println("1.3. Поменяли элемент: " + strings.setByIndex(1, "Два") + ".");

            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println();

            // 1.4. Удаление элемента по индексу, пусть выдает значение элемента.
            System.out.println("1.4. Удаляем элемент с индексом 2: " + strings.deleteByIndex(2) + ".");
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println("1.4. Удаляем элемент с индексом 0: " + strings.deleteByIndex(0) + ".");
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println("1.4. Удаляем элемент с индексом 1: " + strings.deleteByIndex(1) + ".");
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println("1.4. Удаляем элемент с индексом 0: " + strings.deleteByIndex(0) + ".");
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");

            // Проверим длину списка.
            System.out.println("Длина нашего списка равна: " + strings.getLength() + ".");
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
            strings.addByIndex(2, "Четыре");
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println("1.6. Вставляем новый элемент \"Ноль\" индексом 0.");
            strings.addByIndex(0, "Ноль");
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println("1.6. Вставляем новый элемент \"Три\" индексом 3.");
            strings.addByIndex(3, "Три");
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println();

            // 1.7. Удаление узла по значению, пусть выдает true, если элемент был удален.
            System.out.println("1.7. Удаляем элемент \"Ноль\". Удалили? - " + strings.deleteByData("Ноль"));
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println("1.7. Удаляем элемент \"Четыре\". Удалили? - " + strings.deleteByData("Четыре"));
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println("1.7. Удаляем элемент \"Два\". Удалили? - " + strings.deleteByData("Два"));
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println("1.7. Удаляем элемент \"Три\". Удалили? - " + strings.deleteByData("Три"));
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println("1.7. Удаляем элемент \"Один\". Удалили? - " + strings.deleteByData("Один"));
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println();

            // Восстановим список.
            System.out.println("Восстановим список.");
            strings.addFirst("One");
            strings.addByIndex(1, "Two");
            strings.addByIndex(2, null);
            strings.add("Four");
            strings.addByIndex(0, "Zero");
            System.out.println("Наш список теперь есть: " + strings);
            // Проверим длину списка.
            System.out.println("Длина нашего списка равна: " + strings.getLength() + ".");
            System.out.println("=============================");
            System.out.println();

            // 1.8. Удаление первого элемента, пусть выдает значение элемента.
            System.out.println("1.8. Удаляем первый элемент: " + strings.deleteFirst());
            System.out.println("Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println();

            // Проверим длину списка.
            System.out.println("Длина нашего списка равна: " + strings.getLength() + ".");
            System.out.println();

            //  1.9. Разворот списка за линейное время.
            strings.reverse();
            System.out.println("1.9. Разворачиваем список. Наш список теперь есть: " + strings);
            System.out.println("=============================");
            System.out.println();

            // 1.10. Копирование списка.
            System.out.println("1.10. Копирование списка.");
            //stringsCopy.reverse(); // Развернем обратно копию.
            System.out.println("Наш исходный список: " + strings);
            List<String> stringsCopy = strings.copy();
            System.out.println("Копия исходного списка: " + stringsCopy);
            System.out.println("=============================");
            System.out.println(stringsCopy.getLength());
            System.out.println();

            // 1.11. Проверим работу исключения.
            System.out.println("1.11. Проверим работу исключения. Запросим элемент списка с индексом 4.");
            System.out.println(strings.getByIndex(4));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}