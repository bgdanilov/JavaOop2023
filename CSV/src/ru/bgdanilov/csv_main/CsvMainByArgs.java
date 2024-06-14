package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlConverter;
import ru.bgdanilov.csv.CsvToHtmlConverterArgs;
import ru.bgdanilov.csv.CsvToHtmlConverterArgsLoader;
import ru.bgdanilov.csv.Utilities;

import java.io.IOException;

public class CsvMainByArgs {
    public static void main(String[] args) {
        CsvToHtmlConverterArgsLoader loader = new CsvToHtmlConverterArgsLoader();
        CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();

        try {
            CsvToHtmlConverterArgs converterArgs = loader.loadArguments(args);

            if (!converterArgs.getMessages().isEmpty()) {
                Utilities.printMessages(converterArgs.getMessages());
                return;
            }

            csvConverter.convert(converterArgs);

            if (csvConverter.isSuccess()) {
                System.out.println("Файл: " + converterArgs.getCsvFileName() + " успешно обработан.");
                System.out.println("Результат: " + converterArgs.getHtmlFileName() + ".");
            } else {
                System.out.println("Файл: " + converterArgs.getCsvFileName() + " не существует или ошибка его обработки.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка в аргументах: " + e.getMessage());
        }

        // Можно также использовать Логи.
        // Utilities.printMessages(csvConverter.getLogs());
    }
}