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
            System.out.println(shape);
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

        // Проверим equals.
        Circle circleR1 = new Circle(1); // Новый объект окружность R = 1.
        Circle circleR2 = new Circle(2); // Новый объект окружность R = 2.

        System.out.println("1. Проверим, что в массиве тоже окружность радиусом = 1:");
        System.out.println(shapes.get(3));
        System.out.println(circleR1.equals(shapes.get(3))); // true.
        System.out.println();

        System.out.println("2. Радиусы разные:");
        System.out.println(circleR2.equals(shapes.get(3))); // false.
        System.out.println();

        System.out.println("3. Проверим, что в массиве треугольник:");
        System.out.println(shapes.get(2));
        System.out.println("Окружность circleR1 = треугольнику?");
        System.out.println(circleR1.equals(shapes.get(2))); // false.
        System.out.println();

        System.out.println("4. Проверим треугольники:");
        System.out.println(new Triangle(1, 1, 4, 1, 4, 4).equals(shapes.get(2)));
        System.out.println(new Triangle(0, 1, 4, 1, 4, 4).equals(shapes.get(2)));

        System.out.println("5. Проверим Хэш разных окружностей:");
        // Разные объекты не равны.
        System.out.println(circleR1.hashCode());
        System.out.println(circleR2.hashCode());

        // Разные объекты равны.
        System.out.println(circleR2.hashCode());
        System.out.println(new Circle(2).hashCode());
    }
}

/* TODO
    1. здесь можно использовать Arrays.asList - сделать когда пройдем теорию.
    2. Тут foreach так же работает со списком, как с обычным массивом?
 */