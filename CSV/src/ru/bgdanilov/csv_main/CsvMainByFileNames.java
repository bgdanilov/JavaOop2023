package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlConverter;

public class CsvMainByFileNames {
    public static void main(String[] args) {
        CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();

        try {
            csvConverter.convert("csv11.csv", ',');
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            csvConverter.convert("csv2.csv", "mineNamedFile.html", ',');
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}