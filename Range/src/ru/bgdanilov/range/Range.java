package ru.bgdanilov.range;

public class Range {
    private double from;
    private double to;

    // 1. Конструктор.
    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    // 2. Геттеры - сеттеры.
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
    public Range calcIntersection(Range range) {
        if (to <= range.from || range.to <= from || range.to == range.from) {
            return null;
        }

        double from = Math.max(this.from, range.from);
        double to = Math.min(this.to, range.to);

        return new Range(from, to);
    }

    // 7. Объединение двух интервалов.
    // - интервал, содержащий в себе все элементы исходных интервалов.
    public Range[] calcUnion(Range range) {
        // Интервалы вообще не пересекаются (по одной точке - пересечение засчитываем).
        if (this.to < range.from || range.to < this.from) {
            return new Range[]{new Range(from, to), new Range(range.from, range.to)};
        } else {
            // Интервалы пересекаются.
            double from = Math.min(this.from, range.from);
            double to = Math.max(this.to, range.to);

            return new Range[]{new Range(from, to)};
        }
    }

    // 8. Разность.
    // - интервал (интервалы), в который входят все элементы первого интервала, не входящие во второй.
    public Range[] calcDifference(Range range) {
        // Интервалы не пересекаются.
        if (calcIntersection(range) == null) {
            return new Range[]{this};
        }

        // Интервал лежит внутри другого интервала:
        // - интервалы совпадают или исходный интервал внутри вычитаемого.
        if (from >= range.from && to <= range.to) {
            return null;
        } else {
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
    }

    public static String composeLine(Range range) {
        if (range == null) {
            return "[null]";
        }

        return "[" + range.from + ", " + range.to + "]";
    }

    public static String composeLine(Range[] ranges) {
        if (ranges == null) {
            return "[null]";
        }
        StringBuilder sb = new StringBuilder().append('{');

        for (Range range : ranges) {
            sb.append('[').append(range.from).append(", ").append(range.to).append(']')
                    .append(", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append('}');

        return sb.toString();
    }
}