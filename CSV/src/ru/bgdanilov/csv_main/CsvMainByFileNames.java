package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.Utilities;
import ru.bgdanilov.csv.CsvToHtmlConverter;

import java.io.IOException;

public class CsvMainByFileNames {
    public static void main(String[] args) {
        Utilities utilities = new Utilities();
        CsvToHtmlConverter converter = new CsvToHtmlConverter(utilities);

        try {
            converter.convert("csv1.csv", ',');
            converter.convert("csv2.csv", "mineNamedFile.html", ',');
            utilities.printMessages(converter.getLogsList());
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}