package ru.bgdanilov.shape_main;
import ru.bgdanilov.shape.Square;

public class ShapeMain {
    public static void main(String[] args) {
        Square square = new Square(5);

        // Так не получится, т.к. поле private, нужно делать public.
        // System.out.println(square.sideLength);

        System.out.println(square.getSideLength());
        System.out.println(square.getArea());
    }
}