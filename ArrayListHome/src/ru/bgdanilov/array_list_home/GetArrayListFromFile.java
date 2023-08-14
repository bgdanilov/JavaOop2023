package ru.bgdanilov.array_list_home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Задача «ArrayListHome»
// 1. Прочитать в список все строки из файла.
public class GetArrayListFromFile {
    public static void main(String[] args) throws FileNotFoundException {
        // Создаем новый объект списка типа String
        ArrayList<String> strings = new ArrayList<>();

        // Создаем сканнер от FileInputStream(имя файла)
        try (Scanner scanner = new Scanner(new FileInputStream("ArrayListHome/input.txt"))) {

            while (scanner.hasNextLine()) {
                // Добавляем построчно элементы в список-масив.
                strings.add(scanner.nextLine());
            }
        }

        System.out.println(strings);
    }
}