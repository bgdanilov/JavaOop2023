package ru.bgdanilov.range_main;

import ru.bgdanilov.range.Range;

public class MainWithAStar {
    public static void main(String[] args) {
        Range range1 = new Range(0, 3);
        Range range2 = new Range(2, 10);

        Range twoRangesIntersection = range1.getIntersection(range2);

        System.out.printf("Диапазон 1: от %.3f до %.3f.%n", range1.getFrom(), range1.getTo());
        System.out.printf("Диапазон 2: от %.3f до %.3f.%n", range2.getFrom(), range2.getTo());

        if (twoRangesIntersection != null) {
            System.out.printf("Диапазон пересечения: от %.3f до %.3f.%n", twoRangesIntersection.getFrom(), twoRangesIntersection.getTo());
        } else {
            System.out.println("Пересечения нет.");
        }
    }
}