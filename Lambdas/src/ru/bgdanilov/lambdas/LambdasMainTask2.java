// Лямбды. Задача 2.
// Создать бесконечный поток корней чисел.
// С консоли прочитать число – сколько элементов нужно вычислить, затем – распечатать эти элементы.

package ru.bgdanilov.lambdas;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class LambdasMainTask2 {
    public static void main(String[] args) {
        // 1. Считываем число-ограничитель с консоли.
        int rootsAmount = getRootsAmount();

        if (rootsAmount == 0) {
            System.out.println("Вы выбрали \"выход\". Программа завершена.");
            return;
        }

        // 2. Сначала создаем Stream целых чисел.
        // 3. Ограничиваем поток целых чисел, вычисляем корни, ограничиваем, создаем список.
        List<Double> squareRoots = IntStream.iterate(1, number -> number + 1)
                .mapToDouble(Math::sqrt)
                .limit(rootsAmount)
                .boxed()
                .toList();

        System.out.println("Вычисленный список корней:");
        System.out.println(squareRoots);
    }

    public static int getRootsAmount() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        String greetingText = "Введите целое число от 1 до 100 или 0 для выхода: ";

        System.out.println(greetingText);

        // Или 0-9 (одна цифра) или 0-9 (две цифры) или "100".
        Pattern pattern = Pattern.compile("\\d|\\d{2}|100");

        while (!scanner.hasNext(pattern)) {
            scanner.next();
            System.out.println("Неизвестная команда. Попробуйте еще раз.");
            System.out.println(greetingText);
        }

        return scanner.nextInt();
    }
}