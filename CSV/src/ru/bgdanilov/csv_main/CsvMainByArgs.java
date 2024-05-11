package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CsvToHtmlBySymbols;
import ru.bgdanilov.csv.Settings;

import java.io.IOException;

public class CsvMainByArgs {
    public static void main(String[] args) {
        // Используйте args[] или можно задать аргументы через Edit Configurations.
        // В процессе загрузки настроек, записываются ошибки в список messages.
        Settings settings = new Settings(args);

        // Приступаем к конвертированию, только если все настройки прочитались без ошибок.
        if (settings.isWarnings()) {
            settings.printWarnings();
            return;
        }

        CsvToHtmlBySymbols csvConverter = new CsvToHtmlBySymbols(settings);

        try {
            csvConverter.convert();
            csvConverter.printLogs();
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}