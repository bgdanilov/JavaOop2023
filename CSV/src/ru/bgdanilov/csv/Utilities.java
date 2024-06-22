package ru.bgdanilov.csv;

import java.util.ArrayList;

// Класс с общими для других классов методами.
public class Utilities {
    public static String composeMessage(ArrayList<String> list) {
        String lineSeparator = System.lineSeparator();
        return String.join(lineSeparator, list);
    }
}