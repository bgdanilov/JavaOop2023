package ru.bgdanilov.shapes;

public record Rectangle(double height, double width) implements Shape {
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
        return getClass().getSimpleName() + ", высота = " + height + ", ширина = " + width;
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