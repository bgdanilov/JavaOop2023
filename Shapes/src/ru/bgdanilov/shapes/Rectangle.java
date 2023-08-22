package ru.bgdanilov.shapes;

// Оставим этот класс обычным, не record, для демонстрации отличий синтаксиса.
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
        return getClass().getSimpleName() + ", размер " + height + " * " + width;
    }

    @Override
    public boolean equals(Object object) {
        // Передали объект (сам себе равен).
        if (this == object) {
            return true;
        }

        // Объект передали пустой или класса, отличного от сравниваемого.
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        // Приводим Object к Rectangle для сравнения полей.
        Rectangle rectangle = (Rectangle) object;
        return height == rectangle.height && width == rectangle.width;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Double.hashCode(height);
        hash = prime * hash + Double.hashCode(width);

        return hash;
    }
}

/* TODO
    1. IDE мне пишет, что данный класс может быть "a record"
    - почему только Rectangle предлагает сделать Record?
        - потому, что у нас поля final - неизменяемые и нет сеттеров, соответственно,
        а в Square есть сеттер и значения полей можно изменить.
 */