package ru.bgdanilov.shapes;

// Сделаем Square классом-record, как просит IDE.
// record - это класс с неизменными (final) полями и отсутствующими сеттерами.
public record Square(double sideLength) implements Shape {
    @Override
    public double getHeight() {
        return sideLength;
    }

    @Override
    public double getWidth() {
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
        // По-сути  - имя класса.
        return getClass().getSimpleName() + ", сторона = " + sideLength;
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

        // Приводим Object к Square для сравнения полей.
        Square square = (Square) object;
        return sideLength == square.sideLength;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(sideLength);
    }
}

/**
 *  Описание класса. Заметки.
 *  ===================
 *  1. Я понимаю так, что для простого класса с одним полем, можно не переопределять hashCode()?
 *     Или пререопределить на как Double.hashCode() т.к. нам нужен int ?
 *     - В любом случае пришлось бы явно переопределять hashCode, даже с int.
*/