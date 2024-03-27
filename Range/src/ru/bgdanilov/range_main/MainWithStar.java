package ru.bgdanilov.range_main;

import ru.bgdanilov.range.Range;

import java.util.Arrays;

public class MainWithStar {
    public static void main(String[] args) {
        try {
            Range range1 = new Range(0, 10);
            Range range2 = new Range(-10, 1);
            // Range range1 = null;

            // Проверка исключения:
            // Range wrongRange = new Range(100, 10);

            // Проверка Сеттеров на неправильное значение.
            // range1.setFrom(11); // - начало больше конца.
            // range1.setTo(0); // - конец равен началу.

            // Пересечение:
            System.out.println("Пересечение:");
            System.out.println("Интервал 1: " + range1);
            System.out.println("Интервал 2: " + range2);
            System.out.println("Пересечение интервалов 1 и 2: " + range1.getIntersection(range2));
            System.out.println();

            // Объединение:
            System.out.println("Объединение:");
            System.out.println("Интервал 1: " + range1);
            System.out.println("Интервал 2: " + range2);
            System.out.println("Объединение интервалов 1 и 2: " + Arrays.toString(range1.getUnion(range2)));
            System.out.println();

            // Разность:
            System.out.println("Разность:");
            System.out.println("Интервал 1: " + range1);
            System.out.println("Интервал 2: " + range2);
            System.out.println("Разность интервалов 1 и 2: " + Arrays.toString(range1.getDifference(range2)));
            System.out.println();
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}