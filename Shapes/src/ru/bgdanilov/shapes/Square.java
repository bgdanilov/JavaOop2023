package ru.bgdanilov.shapes;

/**
 * Сделаем Square классом-record, как просит IDE.
 * record - это класс с неизменными (final) полями и отсутствующими сеттерами.
 */
public record Square(double sideLength) implements Shape {
    @Override
    public double height() {
        return sideLength;
    }

    @Override
    public double width() {
        return sideLength;
    }

    @Override
    public double getPerimeter() {
        return sideLength * 4;
    }

    @Override
    public double getArea() {
        return sideLength * sideLength;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ", сторона = " + sideLength;
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

        // Приводим Object к Square для сравнения полей.
        Square square = (Square) object;
        return sideLength == square.sideLength;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(sideLength);
    }
}