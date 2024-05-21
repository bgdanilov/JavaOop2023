package ru.bgdanilov.csv;

import java.util.ArrayList;

// Класс с общими для других классов методами.
public class Utilities {
    public static void printMessages(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        for (String item : list) {
            sb.append(item).append(lineSeparator);
        }

        sb.setLength(sb.length() - 1);

        System.out.println(sb);
    }
}