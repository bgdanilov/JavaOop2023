package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.Commons;
import ru.bgdanilov.csv.CsvToHtmlConverter;
import ru.bgdanilov.csv.CommandLineArgs;

import java.io.IOException;

public class CsvMainByArgs {
    public static void main(String[] args) {
        // Используйте передачу аргументов через Edit Configurations.
        // В процессе загрузки аргументов, записываются ошибки в список warningsList.
        Commons commons = new Commons();
        CommandLineArgs arguments = new CommandLineArgs(args, commons);

        // Приступаем к конвертированию, только если все аргументы прочитались и обработались без ошибок.
        if (arguments.isWarnings()) {
            commons.printMessages(arguments.getWarningsList());
            return;
        }

        // Создаем Конвертер.
        CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();

        try {
            csvConverter.convert(arguments);
            commons.printMessages(csvConverter.getLogsList());
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}