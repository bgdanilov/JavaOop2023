package ru.bgdanilov.csv;

import java.util.ArrayList;

// Класс с общими для других классов методами.
public class Utilities {
    // Пусть пока будет. Может переделывать придется и метод снова пригодится.
    public static void printMessages(ArrayList<String> list) {
        String lineSeparator = System.lineSeparator();
        System.out.println(String.join(lineSeparator, list));
    }

    public static String composeMessageLine(ArrayList<String> list) {
        String lineSeparator = System.lineSeparator();
        return String.join(lineSeparator, list);
    }
}