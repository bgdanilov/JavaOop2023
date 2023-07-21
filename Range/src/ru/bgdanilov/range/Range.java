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

    // Пересечение двух интервалов.
    /*
    Два интервала
    range1.from----range1.to . range2.from-----range2.to
    range2.from----range2.to . range1.from-----range1.to

    не пересекаются, если:
    range1.to <= range2.from || range2.to <= range1.from
    (если диапазоны пересекаются только по 1 концу - пересечения нет)

    В объекте есть метод встретить второй объект и родить новый объект.
     */
    public Range getIntersection(Range range2) {
        if (this.to <= range2.from || range2.to <= this.from) {
            return null;
        }

        double from = Math.max(this.from, range2.from);
        double to = Math.min(this.to, range2.to);

        return new Range(from, to);
    }
}