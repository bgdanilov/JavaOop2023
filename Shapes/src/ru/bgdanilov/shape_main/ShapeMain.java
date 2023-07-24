package ru.bgdanilov.shape_main;
import ru.bgdanilov.shape.Square;
import ru.bgdanilov.shape.Triangle;

public class ShapeMain {
    public static void main(String[] args) {
        // Квадрат:
        Square square = new Square(5);
        System.out.println(square.getSideLength());
        System.out.println(square.getArea());

        // Треугольник:
        Triangle triangle = new Triangle(1, 1, 4, 1, 4, 4);
        System.out.println(triangle.getPerimeter());
        System.out.println(triangle.getArea());
    }
}