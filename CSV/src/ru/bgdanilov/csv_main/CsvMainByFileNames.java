package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlBySymbols;
import ru.bgdanilov.csv.Settings;

import java.io.IOException;

public class CsvMainByFileNames {
    public static void main(String[] args) {
        Settings settings = new Settings();
        CsvToHtmlBySymbols csvConverter = new CsvToHtmlBySymbols(settings);

        try {
            csvConverter.convert("csv1.csv", ',');
            csvConverter.convert("csv2.csv", "mineNamedFile.html", ',');
            csvConverter.printLogs();
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}