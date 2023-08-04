package ru.bgdanilov.shapes;

public class Rectangle implements Shape {
    private final double height;
    private final double width;

    public Rectangle(double height, double width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getPerimeter() {
        return (height + width) * 2;
    }

    @Override
    public double getArea() {
        return height * width;
    }

    // Переопределение toString.
    @Override
    public String toString() {
        // По-сути  - имя класса.
        String shapeType = getClass().getSimpleName();

        return shapeType + ", размер " + height + " * " + width;
    }
}

/* TODO
    1. IDE мне пишет, что данный класс может быть "a record"
    - почитать, что это такое и в чем разница.
 */