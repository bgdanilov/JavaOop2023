package ru.bgdanilov.array_list_home;

import java.io.*;
import java.util.ArrayList;

// Задача «ArrayListHome»
// 1. Прочитать в список все строки из файла.
public class GetArrayListFromFile {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ArrayListHome/input.txt"))) {
            ArrayList<String> fileLines = new ArrayList<>();

            String line;

            while ((line = reader.readLine()) != null) {
                // Добавляем построчно элементы в список-массив.
                fileLines.add(line);
            }

            System.out.println(fileLines);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден! " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла! " + e.getMessage());
        }
    }
}