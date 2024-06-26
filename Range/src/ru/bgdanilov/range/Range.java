package ru.bgdanilov.range;

public class Range {
    private double from;
    private double to;

    // 1. Конструктор.
    public Range(double from, double to) {
        if (from >= to) {
            throw new IllegalArgumentException("Начало \"" + from + "\" должно быть меньше конца \"" + to + "\".");
        }

        this.from = from;
        this.to = to;
    }

    // 2. Геттеры - сеттеры.
    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        if (from >= to) {
            throw new IllegalArgumentException("Начало \"" + from + "\" должно быть меньше конца \"" + to + "\".");
        }

        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        if (to <= from) {
            throw new IllegalArgumentException("Конец \"" + to + "\" должен быть больше начала \"" + from + "\".");
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
        return number >= from && number <= to;
    }

    // 6. Пересечение двух интервалов.
    // - интервал, принадлежат те и только те элементы, которые одновременно принадлежат всем данным интервалам.
    // (по одной точке пересечение НЕ засчитываем).
    public Range getIntersection(Range range) {
        // Пересечения нет.
        if (range.from >= to || range.to <= from) {
            return null;
        }

        // Пересечение есть.
        double resultFrom = Math.max(from, range.from);
        double resultTo = Math.min(to, range.to);

        return new Range(resultFrom, resultTo);
    }

    // 7. Объединение двух интервалов.
    // - интервал, содержащий в себе все элементы исходных интервалов.
    public Range[] getUnion(Range range) {
        // Интервалы вообще не пересекаются (по одной точке - пересечение засчитываем).
        if (range.from > to || range.to < from) {
            return new Range[]{new Range(from, to), new Range(range.from, range.to)};
        }

        // Интервалы пересекаются.
        double resultFrom = Math.min(from, range.from);
        double resultTo = Math.max(to, range.to);

        return new Range[]{new Range(resultFrom, resultTo)};
    }

    // 8. Разность.
    // - интервал (интервалы), в который входят все элементы первого интервала, не входящие во второй.
    public Range[] getDifference(Range range) {
        // Интервалы не пересекаются.
        if (to <= range.from || range.to <= from) {
            return new Range[]{new Range(from, to)};
        }

        // Интервал лежит внутри другого интервала:
        // - интервалы совпадают или исходный интервал внутри вычитаемого.
        if (range.from <= from && range.to >= to) {
            return new Range[0];
        }

        // - перекрытие слева.
        if (range.from <= from) {
            return new Range[]{new Range(range.to, to)};
        }

        // - перекрытие справа.
        if (range.to >= to) {
            return new Range[]{new Range(from, range.from)};
        }

        // - перекрытие по середине.
        return new Range[]{new Range(from, range.from), new Range(range.to, to)};
    }

    @Override
    public String toString() {
        return "(" + from + "; " + to + ")";
    }
}

// NullPointerException (range == null) - генерируется самой системой и ловится в main.
