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
    }
}

/* TODO
    1. Повторить почему можно не писать "new Shape[]" ?
    Shape[] shapes = new Shape[] {square1, triangle1, rectangle1, circle1};
    что-то связано с невозможностью использования массива в return.
    2. Что тут происходит?
    System.out.println(Arrays.toString(new Shape[] {e}));

 */