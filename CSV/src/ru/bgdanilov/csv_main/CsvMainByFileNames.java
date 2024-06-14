package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlConverter;

import java.io.IOException;

public class CsvMainByFileNames {
    public static void main(String[] args) {
        try {
            CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();

            // Жаль, но тут программа споткнется:
            // если файл не существует - следующий уже рассматривать не будет.
            csvConverter.convert("csv11.csv", ',');
            csvConverter.convert("csv2.csv", "mineNamedFile.html", ',');
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}