// Лямбды. Задача 2.
// Создать бесконечный поток корней чисел.
// С консоли прочитать число – сколько элементов нужно вычислить, затем – распечатать эти элементы.

package ru.bgdanilov.lambdas_main;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.regex.Pattern;

public class LambdasMainTask2 {
    public static void main(String[] args) {
        int inputNumber = getNumber();

        if (inputNumber == 0) {
            System.out.println("Вы выбрали \"выход\". Программа завершена.");
            return;
        }

        Stream<Double> sqrt = Stream
                .iterate(1, number -> ++number)
                //.map(x -> Math.sqrt(number)) // каждый элемент меняем на корень.
                .map(Math::sqrt) // каждому элементу применяем sqrt из класса Math.
                .limit(inputNumber);

        List<Double> mylist = sqrt.toList();

        System.out.println(mylist);
    }

    public static int getNumber() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.printf("Введите целое число от 1 до 100 или 0 для выхода:%n");

        // Или 0-9 (одна цифра) или 0-9 (две цифры) или "100".
        Pattern pattern = Pattern.compile("\\d|\\d{2}|100");

        while (!scanner.hasNext(pattern)) {
            scanner.next();
            System.out.println("Неизвестная команда. Попробуйте еще раз:");
            System.out.printf("Введите целое число от 1 до 100 или 0 для выхода:%n");
        }

        return scanner.nextInt();
    }
}