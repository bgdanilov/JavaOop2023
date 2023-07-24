package ru.bgdanilov.shape;

public class Triangle implements Shape {
    // Не буду делать геттеры и сеттеры - 6 координат, 12 функций!
    // Будем считать, что как родили треугольник, так и нечего его менять.
    // Делаем поля final, т.к. нет сеттеров и это константы по смыслу тогда.
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final double x3;
    private final double y3;
    private final double sideALength;
    private final double sideBLength;
    private final double sideCLength;

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

        sideALength = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        sideBLength = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        sideCLength = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
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
        return sideALength + sideBLength + sideCLength;
    }

    @Override
    public double getArea() {
        double halfPerimeter = getPerimeter() / 2;

        return Math.sqrt(halfPerimeter
                * (halfPerimeter - sideALength) * (halfPerimeter - sideBLength) * (halfPerimeter - sideCLength));
    }
}