package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlBySymbols;

import java.io.IOException;

public class CsvMain {
    public static void main(String[] args) {
        CsvToHtmlBySymbols csvParser = new CsvToHtmlBySymbols(',');

        try {
            csvParser.parseCsvToHtml("CSV/src/files/csv1.csv", "CSV/src/files/html.html");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}