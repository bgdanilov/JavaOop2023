package ru.bgdanilov.shapes;

/**
 * Сделаем Square классом-record, как просит IDE.
 * record - это класс с неизменными (final) полями и отсутствующими сеттерами.
 */
public record Circle(double radius) implements Shape {
    @Override
    public double height() {
        return radius * 2;
    }

    @Override
    public double width() {
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
        return getClass().getSimpleName() + ", радиус = " + radius;
    }

    @Override
    public boolean equals(Object object) {
        // Ссылаемся на тот же самый объект (ссылки равны).
        if (this == object) {
            return true;
        }

        // Ссылка пустая или ведет к объекту с классом, отличным от сравниваемого.
        if (object == null || getClass() != object.getClass()) {
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