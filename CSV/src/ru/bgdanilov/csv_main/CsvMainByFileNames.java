package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.Utilities;
import ru.bgdanilov.csv.CsvToHtmlConverter;

import java.io.IOException;

public class CsvMainByFileNames {
    public static void main(String[] args) {
        // Тут вывод сообщений только через Логи,
        // иначе придется создать локальную переменную String csvFileName = csv1.csv.
        // Раз у нас часть через логи, другая часть (CsvMainByArgs) имеет непосредственный вывод сообщений,
        // то как сделать чтобы было однообразно?
        // Только через Логи все?
        CsvToHtmlConverter csvConverter = new CsvToHtmlConverter();

        csvConverter.convert("csv.csv1", ',');
        csvConverter.convert("csv2.csv", "mineNamedFile.html", ',');

        Utilities.printMessages(csvConverter.getLogs());
    }
}