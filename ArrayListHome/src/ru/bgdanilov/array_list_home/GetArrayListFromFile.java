package ru.bgdanilov.array_list_home;

import java.io.*;
import java.util.ArrayList;

// Задача «ArrayListHome»
// 1. Прочитать в список все строки из файла.
public class GetArrayListFromFile {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ArrayListHome/input1.txt"))) {
            // Создаем новый объект списка типа String
            ArrayList<String> strings = new ArrayList<>();

            String line;

            while ((line = reader.readLine()) != null) {
                // Добавляем построчно элементы в список-массив.
                strings.add(line);
            }

            System.out.println(strings);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден! " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла! " + e.getMessage());
        }
        // finally - не нужен, т.к. reader находится в try и закроется автоматически.
    }
}

/*
    Вопросы:
    1. Как сымитировать IOException e ?
 */