package ru.bgdanilov.shape_main;
import ru.bgdanilov.shape.*;
import java.util.Arrays;

public class ShapeMainArraysSort {
    public static void main(String[] args) {
        // Создаем объекты.
        Square square1 = new Square(10);
        Triangle triangle1 = new Triangle(1, 1, 4, 1, 4, 4);
        Rectangle rectangle1 = new Rectangle(2, 4);
        Circle circle1 = new Circle(1);

        // Выводим информацию о фигурах переопределенным toString()
        // Почему, достаточно просто circle1, а circle1.toString ?
        System.out.println(circle1);
        System.out.println(rectangle1);
        System.out.println(square1);
        System.out.println(triangle1);

        // Добавим объекты в Массив.
        Shape[] shapes = new Shape[] {square1, triangle1, rectangle1, circle1};

        // Сортируем массив по убыванию площади.
        Arrays.sort(shapes, new ShapeComparatorByArea());
        System.out.println("Максимальная площадь среди массива фигур равна "
                + shapes[0].getArea());

        // Сортируем массив по убыванию периметра.
        Arrays.sort(shapes, new ShapeComparatorByPerimeter());
        System.out.println("Второй по величине периметр среди массива фигур равен "
                + shapes[1].getPerimeter());
    }
}