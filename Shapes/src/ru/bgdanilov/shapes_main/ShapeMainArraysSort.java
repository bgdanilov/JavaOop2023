package ru.bgdanilov.shapes_main;

import ru.bgdanilov.shapes.*;
import ru.bgdanilov.shapes_comparators.ShapesAreaComparator;
import ru.bgdanilov.shapes_comparators.ShapesPerimeterComparator;

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
            System.out.println(Arrays.toString(new Shape[]{shape}));
        }

        // Сортируем массив по убыванию площади.
        Arrays.sort(shapes, new ShapesAreaComparator().reversed());
        System.out.println("Максимальная площадь среди массива фигур равна "
                + shapes[0].getArea());

        // Сортируем массив по убыванию периметра.
        Arrays.sort(shapes, new ShapesPerimeterComparator().reversed());
        System.out.println("Второй по величине периметр среди массива фигур равен "
                + shapes[1].getPerimeter());

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

/* TODO
    1. Повторить почему можно не писать "new Shape[]" ?
    Shape[] shapes = new Shape[] {square1, triangle1, rectangle1, circle1};
    что-то связано с невозможностью использования массива в return.
    2. Что тут происходит?
    System.out.println(Arrays.toString(new Shape[] {e}));
 */