package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.Utilities;
import ru.bgdanilov.csv.CsvToHtmlConverter;
import ru.bgdanilov.csv.CommandLineArgs;

import java.io.IOException;

public class CsvMainByArgs {
    public static void main(String[] args) {
        // Используйте передачу аргументов через Edit Configurations.
        // В процессе загрузки аргументов, записываются ошибки в список warningsList.
        Utilities utilities = new Utilities();
        CommandLineArgs arguments = new CommandLineArgs(args, utilities);

        // Приступаем к конвертированию, только если все аргументы прочитались и обработались без ошибок.
        if (arguments.isWarnings()) {
            utilities.printMessages(arguments.getWarningsList());
            return;
        }

        // Создаем Конвертер.
        CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();

        try {
            csvConverter.convert(arguments);
            utilities.printMessages(csvConverter.getLogsList());
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}