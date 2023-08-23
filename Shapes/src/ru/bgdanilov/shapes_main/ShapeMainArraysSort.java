package ru.bgdanilov.shapes_main;

import ru.bgdanilov.shapes.*;
import ru.bgdanilov.shapes_comparators.ShapeAreaComparator;
import ru.bgdanilov.shapes_comparators.ShapePerimeterComparator;

import java.util.Arrays;

public class ShapeMainArraysSort {
    public static void main(String[] args) {
        // Добавим объекты в Массив.
        Shape[] shapes = {
                new Square(10),
                new Triangle(1, 1, 4, 1, 4, 4),
                new Rectangle(2, 4),
                new Circle(1)
        };

        // Выводим информацию о фигурах переопределенным toString()
        // println() автоматически вызывает toString(), поэтому не надо ее писать.
        for (Shape shape : shapes) {
            System.out.println(shape);
        }

        System.out.println();

        // Сортируем массив по убыванию площади.
        Arrays.sort(shapes, new ShapeAreaComparator().reversed());
        System.out.println("Максимальная площадь среди массива фигур равна "
                + shapes[0].getArea());

        // Сортируем массив по убыванию периметра.
        Arrays.sort(shapes, new ShapePerimeterComparator().reversed());
        System.out.println("Второй по величине периметр среди массива фигур равен "
                + shapes[1].getPerimeter());
        System.out.println();

        // Проверим equals.
        Circle circleR1 = new Circle(1); // Новый объект окружность R = 1.
        Circle circleR2 = new Circle(2); // Новый объект окружность R = 2.

        System.out.println("1. Проверим, что в массиве тоже окружность R = 1:");
        System.out.println(shapes[3]);
        System.out.println(circleR1.equals(shapes[3])); // true.
        System.out.println();

        System.out.println("2. Радиусы разные:");
        System.out.println(circleR2.equals(shapes[3])); // false.
        System.out.println();

        System.out.println("3. Проверим, что в массиве треугольник:");
        System.out.println(shapes[2]);
        System.out.println("Окружность circleR1 = треугольнику?");
        System.out.println(circleR1.equals(shapes[2])); // false.
        System.out.println();

        System.out.println("4. Проверим треугольники:");
        System.out.println(new Triangle(1, 1, 4, 1, 4, 4).equals(shapes[2]));
        System.out.println(new Triangle(0, 1, 4, 1, 4, 4).equals(shapes[2]));
    }
}