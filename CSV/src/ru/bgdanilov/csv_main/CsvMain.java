package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.Parser;

public class CsvMain {
    public static void main(String[] args) {
        String lineSeparator = System.lineSeparator();
        Parser parser = new Parser("csv3.csv", ';', lineSeparator);

        parser.parseFile();
    }
}