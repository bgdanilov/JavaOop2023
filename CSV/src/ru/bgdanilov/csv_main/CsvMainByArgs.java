package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlConverter;
import ru.bgdanilov.csv.CsvToHtmlConverterArgs;
import ru.bgdanilov.csv.CsvToHtmlConverterArgsLoader;

import java.io.IOException;

public class CsvMainByArgs {
    public static void main(String[] args) {
        try {
            CsvToHtmlConverterArgsLoader argsLoader = new CsvToHtmlConverterArgsLoader();
            CsvToHtmlConverterArgs converterArgs = argsLoader.loadArguments(args);

            CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();
            csvConverter.convert(converterArgs);
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}