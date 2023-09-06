package ru.bgdanilov.shapes;

public class Triangle implements Shape {
    // Делаем поля final, т.к. нет сеттеров и это константы по смыслу тогда.
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final double x3;
    private final double y3;
    private final double side1Length;
    private final double side2Length;
    private final double side3Length;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;

        side1Length = getSideLength(x1, y2, x2, y2);
        side2Length = getSideLength(x2, y2, x3, y3);
        side3Length = getSideLength(x3, y3, x1, y1);
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getX3() {
        return x3;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public double getY3() {
        return y3;
    }

    public double getSide1Length() {
        return side1Length;
    }

    public double getSide2Length() {
        return side2Length;
    }

    public double getSide3Length() {
        return side3Length;
    }

    private static double getSideLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
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
        return side1Length + side2Length + side3Length;
    }

    @Override
    public double getArea() {
        double halfPerimeter = getPerimeter() / 2;

        return Math.sqrt(halfPerimeter
                * (halfPerimeter - side1Length) * (halfPerimeter - side2Length) * (halfPerimeter - side3Length));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ", "
                + "(" + x1 + "; " + y2 + "), "
                + "(" + x2 + "; " + y2 + "), "
                + "(" + x3 + "; " + y3 + ")";
    }

    @Override
    public boolean equals(Object object) {
        // Передали объект (сам себе равен).
        if (this == object) {
            return true;
        }

        // Объект передали пустой или класса, отличного от сравниваемого.
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        // Приводим Object к Triangle для сравнения полей.
        Triangle triangle = (Triangle) object;
        return x1 == triangle.x1
                && x2 == triangle.x2
                && x3 == triangle.x3
                && y1 == triangle.y1
                && y2 == triangle.y2
                && y3 == triangle.y3;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Double.hashCode(x1);
        hash = prime * hash + Double.hashCode(x2);
        hash = prime * hash + Double.hashCode(x3);
        hash = prime * hash + Double.hashCode(y1);
        hash = prime * hash + Double.hashCode(y2);
        hash = prime * hash + Double.hashCode(y3);

        return hash;
    }
}

/*
 *  Заметки.
 *  ===================
 *  1. Почему этот клас не предлагается сделать record ? Вроде поля final, сеттеров нет...
 *     - Конструктор записи не сможет вычислить эти значения, он может только присвоить им значения аргументов.
 *     - Поэтому здесь не получится использовать запись.
 *
 *  2. getSideLength.
 *     Делаем private - значит этот метод доступен только внутри класса Triangle,
 *     а нам только тут и надо.
 *     Делаем static, т.к. не используется за пределами данного класса.
 *
 *  3. Переменные объявленные в методе - недоступны нигде кроме этого метода.
 *     Что бы переменная стала доступна другим методам ее нужно объявить в классе.
 *     Такая переменная называется полем класса.
 *     Значит можно инициализировать нужные поля - внутренние переменные класса в конструкторе,
 *     при этом НЕ нужно их инициализировать при создании объекта класса.
 *     В Main будет без длин сторон:
 *     Triangle triangle = new Triangle(1, 1, 4, 1, 4, 4); !!!!
 */