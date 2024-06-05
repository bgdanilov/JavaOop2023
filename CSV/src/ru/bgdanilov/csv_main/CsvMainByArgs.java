package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlConverter;
import ru.bgdanilov.csv.CsvToHtmlConverterArgs;
import ru.bgdanilov.csv.CsvToHtmlConverterArgsLoader;
import ru.bgdanilov.csv.Utilities;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CsvMainByArgs {
    public static void main(String[] args) {
        CsvToHtmlConverterArgs converterArgs = new CsvToHtmlConverterArgs();
        CsvToHtmlConverterArgsLoader loader = new CsvToHtmlConverterArgsLoader(args, converterArgs);

        try {
            loader.loadArguments();
            CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();
            csvConverter.convert(converterArgs);
            //Utilities.printMessages(csvConverter.getLogs());

            System.out.println("Файл: " + converterArgs.getCsvFileName() + " успешно обработан.");
            System.out.println("Результат: " + converterArgs.getHtmlFileName() + ".");
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл " + converterArgs.getCsvFileName() + " не существует.");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}