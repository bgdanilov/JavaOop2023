package ru.bgdanilov.shapes;

public class Triangle implements Shape {
    // Делаем поля final, т.к. нет сеттеров и это константы по смыслу тогда.
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final double x3;
    private final double y3;
    private final double side1;
    private final double side2;
    private final double side3;

    // Переменные объявленные в методе - недоступны нигде кроме этого метода.
    // Что бы переменная стала доступна другим методам ее нужно объявить в классе.
    // Такая переменная называется полем класса.
    // Значит можно инициализировать нужные поля - внутренние переменные класса в конструкторе,
    // при этом НЕ нужно их инициализировать при создании объекта класса.
    // В Main будет без длин сторон:
    // Triangle triangle = new Triangle(1, 1, 4, 1, 4, 4); !!!!
    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;

        side1 = getSideLength(x2, x1, y2, y1);
        side2 = getSideLength(x3, x2, y3, y2);
        side3 = getSideLength(x3, x1, y3, y1);
    }

    public double getX1() {
        return this.x1;
    }

    public double getX2() {
        return this.x2;
    }

    public double getX3() {
        return this.x3;
    }

    public double getY1() {
        return this.y1;
    }

    public double getY2() {
        return this.y2;
    }

    public double getY3() {
        return this.y3;
    }

    public double getSide1() {
        return this.side1;
    }

    public double getSide2() {
        return this.side2;
    }

    public double getSide3() {
        return this.side3;
    }

    // Делаем private - значит этот метод доступен только внутри класса Triangle,
    // а нам только тут и надо.
    private double getSideLength(double xTo, double xFrom, double yTo, double yFrom) {
        return Math.sqrt((xTo - xFrom) * (xTo - xFrom) + (yTo - yFrom) * (yTo - yFrom));
    }

    @Override
    public double getHeight() {
        return Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);
    }

    @Override
    public double getWidth() {
        return Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
    }

    @Override
    public double getPerimeter() {
        return side1 + side2 + side3;
    }

    @Override
    public double getArea() {
        double halfPerimeter = getPerimeter() / 2;

        return Math.sqrt(halfPerimeter
                * (halfPerimeter - side1) * (halfPerimeter - side2) * (halfPerimeter - side3));
    }

    @Override
    public String toString() {
        // По-сути  - имя класса.
        String shapeType = getClass().getSimpleName();

        return shapeType + ", площадь = " + getArea();
    }

    @Override
    public boolean equals(Object shape) {
        // Передали объект (сам себе равен).
        if (this == shape) {
            return true;
        }

        // Объект передали пустой или класса, отличного от сравниваемого.
        if (shape == null || this.getClass() != shape.getClass()) {
            return false;
        }

        // Приводим Object к Triangle для сравнения полей.
        Triangle triangle = (Triangle) shape;
        return this.x1 == triangle.x1
                && this.x2 == triangle.x2
                && this.x3 == triangle.x3
                && this.y1 == triangle.y1
                && this.y2 == triangle.y2
                && this.y3 == triangle.y3;
    }
}

/* TODO
    1. Почему этот клас не предлагается сделать record ?
    Вроде поля final, сеттеров нет...
        -- Наверное, потому, что у нас конструктор переписан, кастомный?
 */