package ru.bgdanilov.range_main;

import ru.bgdanilov.range.Range;

public class MainWithStar {
    public static void main(String[] args) {
        Range initialRange = new Range(0, 10);

        // Пересечение:
        System.out.println("Пересечение:");
        System.out.println("Исходный интервал: " + Range.composeLine(initialRange));
        Range intersectingRange = new Range(1, 5);
        System.out.println("Интервал для пересечения: " + Range.composeLine(intersectingRange));
        System.out.println("Пересечение: " + Range.composeLine(initialRange.calcIntersection(intersectingRange)));
        System.out.println();

        // Объединение:
        System.out.println("Объединение:");
        System.out.println("Исходный интервал: " + Range.composeLine(initialRange));
        Range unatedRange = new Range(-10, -4);
        System.out.println("Интервал для объединения: " + Range.composeLine(unatedRange));
        System.out.println("Объединение: " + Range.composeLine(initialRange.calcUnion(unatedRange)));
        System.out.println();

        // Разность:
        System.out.println("Разность:");
        System.out.println("Исходный интервал: " + Range.composeLine(initialRange));
        Range subtractedRange = new Range(-10, -4);
        System.out.println("Интервал для разности: " + Range.composeLine(subtractedRange));
        System.out.println("Разность: " + Range.composeLine(initialRange.calcDifference(subtractedRange)));
        System.out.println();
    }
}