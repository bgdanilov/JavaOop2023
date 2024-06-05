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
        CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();

        // try-catch тут нужен для исключений, которые может бросить loader.loadArguments();
        // Если бы loader.loadArguments(); не бросал исключения, а писал в log, то:
        // - ошибки бы копились, например, можно было бы выдать:
        // "файл не существует" и "[-p]: не указан префикс выходного файла".
        try {
            loader.loadArguments();
            csvConverter.convert(converterArgs);

            if (csvConverter.getSuccess()) {
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