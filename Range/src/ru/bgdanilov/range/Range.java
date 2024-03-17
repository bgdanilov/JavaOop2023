package ru.bgdanilov.range;

public class Range {
    private double from;
    private double to;

    // 1. Конструктор.
    public Range(double from, double to) {
        if (from > to) {
            throw new IllegalArgumentException("Начало [" + from + "] не может быть больше конца [" + to + "].");
        }

        this.from = from;
        this.to = to;
    }

    // 2. Геттеры - сеттеры.
    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        if (from > this.to) {
            throw new IllegalArgumentException("Начало [" + from + "] не может быть больше конца [" + to + "].");
        }

        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        if (to < this.from) {
            throw new IllegalArgumentException("Конец [" + to + "] не может быть меньше начала [" + from + "].");
        }

        this.to = to;
    }

    // Методы:
    // 3. Длина диапазона.
    public double getLength() {
        return to - from;
    }

    // 4. Середина диапазона.
    public double getMiddle() {
        return (to - from) / 2 + from;
    }

    // 5. Принадлежит ли число диапазону?
    public boolean isInside(double number) {
        return from <= number && to >= number;
    }

    // 6. Пересечение двух интервалов.
    // - интервал, принадлежат те и только те элементы, которые одновременно принадлежат всем данным интервалам.
    // (по одной точке пересечение НЕ засчитываем).
    public Range[] calcIntersection(Range range) {
        // Пересечение с пустым множеством - бросает NullPointerException.

        // Пересечение с непустым множеством или одной точкой.
        if (to <= range.from || range.to <= from || range.to == range.from) {
            return new Range[0];
        }

        double from = Math.max(this.from, range.from);
        double to = Math.min(this.to, range.to);

        return new Range[]{new Range(from, to)};
    }

    // 7. Объединение двух интервалов.
    // - интервал, содержащий в себе все элементы исходных интервалов.
    public Range[] calcUnion(Range range) {
        // Объединение с пустым множеством.
        if (range == null) {
            return new Range[]{new Range(from, to)};
        }

        // Интервалы вообще не пересекаются (по одной точке - пересечение засчитываем).
        if (this.to < range.from || range.to < this.from) {
            return new Range[]{new Range(from, to), new Range(range.from, range.to)};
        }

        // Интервалы пересекаются.
        double unitedRangeFrom = Math.min(this.from, range.from);
        double unitedRangeTo = Math.max(this.to, range.to);

        return new Range[]{new Range(unitedRangeFrom, unitedRangeTo)};
    }

    // 8. Разность.
    // - интервал (интервалы), в который входят все элементы первого интервала, не входящие во второй.
    public Range[] calcDifference(Range range) {
        // Вычитание пустого множества.
        if (range == null) {
            return new Range[]{new Range(from, to)};
        }

        // Интервалы не пересекаются.
        if (to <= range.from || range.to <= from || range.to == range.from) {
            return new Range[]{new Range(from, to)};
        }

        // Интервал лежит внутри другого интервала:
        // - интервалы совпадают или исходный интервал внутри вычитаемого.
        if (from >= range.from && to <= range.to) {
            //return null;
            return new Range[0];
        }

        // - перекрытие слева.
        if (range.from <= from) {
            return new Range[]{new Range(range.to, to)};
            // - перекрытие справа.
        } else if (range.to >= to) {
            return new Range[]{new Range(from, range.from)};
        }

        //  - перекрытие по середине.
        return new Range[]{new Range(from, range.from), new Range(range.to, to)};
    }

    @Override
    public String toString() {
        return "[(" + from + "; " + to + ")]";
    }
}