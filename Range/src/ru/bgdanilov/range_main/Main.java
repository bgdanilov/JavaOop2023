package ru.bgdanilov.range_main;

import ru.bgdanilov.range.Range;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Создаем объект класса.
        Range myRange = new Range(2.34, 9.76);

        System.out.printf("Диапазон: от %.3f до %.3f.%n", myRange.getFrom(), myRange.getTo());
        System.out.printf("Длина диапазона: %.3f.%n", myRange.getLength());
        System.out.println("-----------");

        myRange.setFrom(-3); // Меняем поле from.
        myRange.setTo(1); // Меняем поле to.
        System.out.printf("Измененный диапазон: от %.3f до %.3f.%n", myRange.getFrom(), myRange.getTo());

        // Проверяем, лежит ли число в диапазоне.
        double number = -3;
        System.out.printf("Лежит ли число %.3f в диапазоне? %b%n", number, myRange.isInside(number));
        System.out.println("-----------");

        // Программа. Какое из двух чисел лежит ближе к середине диапазона?
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите первое число: ");
        double number1 = scanner.nextDouble();

        System.out.print("Введите второе число: ");
        double number2 = scanner.nextDouble();

        System.out.println("-----------");

        // Ищем середину диапазона.
        double rangeMiddle = myRange.getMiddle();
        System.out.printf("Середина диапазона: %.3f.%n", rangeMiddle);

        double epsilon = 1.0e-10;
        boolean isNumber1Inside = myRange.isInside(number1);
        boolean isNumber2Inside = myRange.isInside(number2);

        if (isNumber1Inside || isNumber2Inside) {
            if ((Math.abs(rangeMiddle - number1) - Math.abs(rangeMiddle - number2)) > epsilon) {
                System.out.printf("Число %.3f ближе к середине диапазона.%n", number2);
            } else if ((Math.abs(rangeMiddle - number2) - Math.abs(rangeMiddle - number1)) > epsilon) {
                System.out.printf("Число %.3f ближе к середине диапазона.%n", number1);
            } else {
                System.out.printf("Оба числа %.3f и %.3f равноудалены от середины диапазона.%n", number1, number2);
            }
        } else {
            System.out.printf("Оба числа %.3f и %.3f не лежат в диапазоне.%n", number1, number2);
        }
    }
}