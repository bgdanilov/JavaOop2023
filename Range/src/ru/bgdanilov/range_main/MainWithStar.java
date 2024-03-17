package ru.bgdanilov.range_main;

import ru.bgdanilov.range.Range;

import java.util.Arrays;

public class MainWithStar {
    public static void main(String[] args) {
        try {
            // Проверка исключения:
            // Range wrongRange = new Range(100, 10);

            // Исходный интервал.
            Range initialRange = new Range(0, 10);
            //Range initialRange = null;

            // Проверка Сеттеров на неправильное значение.
            // initialRange.setFrom(11); // - начало больше конца.
            // initialRange.setTo(-11); // - конец меньше начала.

            // Пересечение:
            System.out.println("Пересечение:");
            System.out.println("Исходный интервал: " + initialRange);
            Range intersectingRange = new Range(-10, 0);
            System.out.println("Интервал для пересечения: " + intersectingRange);
            System.out.println("Пересечение: " + Arrays.toString(initialRange.calcIntersection(intersectingRange)));
            System.out.println();

            // Объединение:
            System.out.println("Объединение:");
            System.out.println("Исходный интервал: " + initialRange);
            Range uniteWithRange = new Range(0, 0);
            System.out.println("Интервал для объединения: " + uniteWithRange);
            System.out.println("Объединение: " + Arrays.toString(initialRange.calcUnion(uniteWithRange)));
            System.out.println();

            // Разность:
            System.out.println("Разность:");
            System.out.println("Исходный интервал: " + initialRange);
            Range subtractedRange = new Range(2, 6);
            System.out.println("Интервал для разности: " + subtractedRange);
            System.out.println("Разность: " + Arrays.toString(initialRange.calcDifference(subtractedRange)));
            System.out.println();
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}