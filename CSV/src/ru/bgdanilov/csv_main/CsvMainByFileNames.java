package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.Utilities;
import ru.bgdanilov.csv.CsvToHtmlConverter;

import java.io.IOException;

public class CsvMainByFileNames {
    public static void main(String[] args) {
        CsvToHtmlConverter converter = new CsvToHtmlConverter();

        try {
            converter.convert("csv1.csv", ',');
            converter.convert("csv2.csv", "mineNamedFile.html", ',');
            Utilities.printMessages(converter.getLogs());
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}