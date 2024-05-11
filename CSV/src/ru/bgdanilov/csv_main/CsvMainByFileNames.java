package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.Commons;
import ru.bgdanilov.csv.CsvToHtmlConverter;

import java.io.IOException;

public class CsvMainByFileNames {
    public static void main(String[] args) {
        Commons commons = new Commons();
        CsvToHtmlConverter converter = new CsvToHtmlConverter(commons);

        try {
            converter.convert("csv1.csv", ',');
            converter.convert("csv2.csv", "mineNamedFile.html", ',');
            commons.printMessages(converter.getLogsList());
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}