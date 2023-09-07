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
        System.out.println();

        // Вызываем методы треугольника, которых нет в интерфейсе
        // или они не используются, чтобы не было warning.
        System.out.println("5. Координаты вершин и длины сторон, высота и ширина треугольника:");
        Triangle shape2 = (Triangle) shapes[2];

        System.out.println("x1 = " + shape2.getX1());
        System.out.println("y1 = " + shape2.getY1());
        System.out.println("x2 = " + shape2.getX2());
        System.out.println("y2 = " + shape2.getY2());
        System.out.println("x3 = " + shape2.getX3());
        System.out.println("y3 = " + shape2.getY3());

        System.out.println("Side1Length = " + shape2.getSide1Length());
        System.out.println("Side2Length = " + shape2.getSide2Length());
        System.out.println("Side3Length = " + shape2.getSide3Length());

        System.out.println("Высота = " + shape2.getHeight());
        System.out.println("Ширина = " + shape2.getWidth());
    }
}

/*
 *  Заметки.
 *  ===================
 *  1. Почему я не могу получить getX1() из фигуры, которая в массиве?
 *     shapes[2].getX1();
 *     - потому, что нужно привести к треугольнику, т.к. не каждая фигура - треугольник.
 *       ((Triangle) shapes[2]).getX1();
 *
 */