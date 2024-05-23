package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.Utilities;
import ru.bgdanilov.csv.CsvToHtmlConverter;
import ru.bgdanilov.csv.CommandLineArgs;

import java.io.IOException;

public class CsvMainByArgs {
    public static void main(String[] args) {
        // Используйте передачу аргументов через Edit Configurations.
        // В процессе загрузки аргументов, записываются ошибки в список warnings.
        CommandLineArgs arguments = new CommandLineArgs(args);

        // Приступаем к конвертированию, только если все аргументы прочитались и обработались без ошибок.
        if (arguments.hasWarnings()) {
            Utilities.printMessages(arguments.getWarnings());
            return;
        }

        // Создаем Конвертер.
        CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();

        try {
            csvConverter.convert(arguments);
            Utilities.printMessages(csvConverter.getLogs());
/*
            Если конвертер отработает правильно, не бросив сообщение,
            то те же самые сообщения можно будет вывести в месте вызова конвертера в try,
            а если будет брошено исключение, то будут выведены другие сообщения из блока catch.
            Поэтому в текущей логике не нужно поле logsList и связанные с ним методы:

            System.out.println("Файл: " + arguments.getCsvFileName() + " успешно обработан.");
            System.out.println("Результат: " + arguments.getHtmlFileName() + ".");

            Но я оставлю пока logs и все, что с ним связано. Мне кажется, это придает больше гибкости.
            Вдруг перепишу на MVC - все-таки список-хранилище можно потом куда угодно передать.
*/
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}