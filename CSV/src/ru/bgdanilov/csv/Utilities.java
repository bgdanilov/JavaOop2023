package ru.bgdanilov.csv;

import java.util.ArrayList;

// Класс с общими для других классов методами.
public class Utilities {
    public static void printMessages(ArrayList<String> list) {
        String lineSeparator = System.lineSeparator();
        System.out.println(String.join(lineSeparator, list));
    }
}