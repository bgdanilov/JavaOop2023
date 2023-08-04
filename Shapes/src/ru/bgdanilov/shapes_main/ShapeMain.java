package ru.bgdanilov.shapes_main;

import ru.bgdanilov.shapes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShapeMain {
    public static void main(String[] args) {
        // Создаем объекты, одновременно добавляя в безразмерный массив-список.
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Square(10));
        shapes.add(new Triangle(1, 1, 4, 1, 4, 4));
        shapes.add(new Rectangle(2, 4));
        shapes.add(new Circle(1));

        // Выводим информацию о фигурах.
        for (Shape shape : shapes) {
            System.out.println(Arrays.toString(new Shape[]{shape}));
        }

        // Сравниваем по площади.
        // Сортируем по площади, по возрастанию (по умолчанию).
        shapes.sort(Comparator.comparing(Shape::getArea));
        // Соответственно, размер списка-1 - это последний, самый большой объект.
        System.out.println("Максимальная площадь среди списка фигур равна: "
                + shapes.get(shapes.size() - 1).getArea());

        // Сортируем по периметру, по возрастанию (по умолчанию).
        // Применяем reversed() - получается по-убыванию.
        shapes.sort(Comparator.comparing(Shape::getPerimeter).reversed());
        // Соответственно, элемент 1 - это второй по-убыванию.
        System.out.println("Второй по величине периметр среди списка фигур равен: "
                + shapes.get(1).getPerimeter());
    }
}

/* TODO
    1. здесь можно использовать Arrays.asList - сделать когда пройдем теорию.
    2. Тут foreach так же работает со списком, как с обычным массивом?

 */