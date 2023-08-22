package ru.bgdanilov.shapes;

// Сделаем Square классом-record, как просит IDE.
// record - это класс с неизменными (final) полями и отсутствующими сеттерами.
public record Circle(double radius) implements Shape {
    @Override
    public double getHeight() {
        return radius * 2;
    }

    @Override
    public double getWidth() {
        return radius * 2;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    // Переопределение toString.
    @Override
    public String toString() {
        // По-сути  - имя класса.
        return getClass().getSimpleName() + ", R = " + radius;
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

        // Приводим Object к Circle для сравнения полей.
        Circle circle = (Circle) object;
        return radius == circle.radius;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(radius);
    }
}