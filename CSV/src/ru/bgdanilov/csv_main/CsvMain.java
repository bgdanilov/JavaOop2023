package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlBySymbols;
import ru.bgdanilov.csv.Messages;
import ru.bgdanilov.csv.Settings;

import java.io.IOException;

public class CsvMain {
    public static void main(String[] args) {
        // Используйте args[] или можно задать аргументы через Edit Configurations.
        //args = new String[]{"point-comma-sv.csv", "-o", "/Documents", "-s", ";", "-p", "prefix-"};
        Messages messages = new Messages();

        Settings settings = new Settings(messages);
        settings.loadSettings(args);

        // В процессе загрузки настроек, записываются ошибки в список messages.
        // Приступаем к конвертированию, только если все настройки прочитались без ошибок.
        if (!messages.getMessages().isEmpty()) {
            System.out.println(messages);
            return;
        }

        CsvToHtmlBySymbols csvConverter = new CsvToHtmlBySymbols(settings);

        try {
            csvConverter.convertCsvToHtml();
            System.out.println("Файл: " + settings.getUtilityHome() + settings.getCsvFileName() + " успешно обработан.");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}