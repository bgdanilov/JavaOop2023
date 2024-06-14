package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlConverter;

import java.io.IOException;

public class CsvMainByFileNames {
    public static void main(String[] args) {
        try {
            CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();

            // Жаль, но тут программа споткнется, файл не существует и следующий уже рассматривать не будет.
            csvConverter.convert("csv1.csv1", ',');
            csvConverter.convert("csv2.csv", "mineNamedFile.html", ',');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}