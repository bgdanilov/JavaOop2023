package ru.bgdanilov.range;

public class Range {
    private double from;
    private double to;

    // Конструктор.
    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    // Геттеры - сеттеры.
    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    // Методы:
    // Длина диапазона.
    public double getLength() {
        return to - from;
    }

    // Середина диапазона.
    public double getMiddle() {
        return (to - from) / 2 + from;
    }

    // Принадлежит ли число диапазону?
    public boolean isInside(double number) {
        double epsilon = 1.0e-10;

        return number - from >= -epsilon && to - number >= -epsilon;
    }
}