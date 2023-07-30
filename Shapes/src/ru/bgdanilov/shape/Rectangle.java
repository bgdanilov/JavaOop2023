package ru.bgdanilov.shape;

public class Rectangle implements Shape {
    private final double sideALength;
    private final double sideBLength;

    public Rectangle(double sideALength, double sideBLength) {
        this.sideALength = sideALength;
        this.sideBLength = sideBLength;
    }

    @Override
    public double getHeight() {
        return sideALength;
    }

    @Override
    public double getWidth() {
        return sideBLength;
    }

    @Override
    public double getPerimeter() {
        return (sideALength + sideBLength) * 2;
    }

    @Override
    public double getArea() {
        return sideALength * sideBLength;
    }

    // Переопределение toString.
    @Override
    public String toString() {
        // По-сути  - имя класса.
        String shapeType = getClass().getSimpleName();

        return shapeType + ", размер "+ sideALength + " * " + sideBLength;
    }
}