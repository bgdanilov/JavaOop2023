// Версия 4.
// Избавимся от влияния settings на конвертер при передаче файлов без args[].
// Создан вспомогательный класс Commons (Общее) для методов, используемых рабочими классами.
package ru.bgdanilov.csv;

import java.io.*;
import java.util.ArrayList;

public class CsvToHtmlConverter {
    Commons commons;
    private final ArrayList<String> logsList = new ArrayList<>();

    public CsvToHtmlConverter() {
    }

    public CsvToHtmlConverter(Commons commons) {
        this.commons = commons;
    }

    // Конвертирование по переданным args[].
    // Использует данные из args[], хранящиеся в объекте arguments класса CommandLineArgs.
    public void convert(CommandLineArgs arguments) throws IOException {
        String csvFileName = arguments.getCsvFileName();
        File csvFile = new File(csvFileName);

        String htmlFileName = arguments.getHtmlFileName();
        File htmlFile = new File(htmlFileName);

        char csvSeparator = arguments.getSeparator();

        if (converter(csvFile, htmlFile, csvSeparator)) {
            setLogs(csvFileName, htmlFileName);
        }
    }

    // Конвертирование непосредственно путем передачи только имени csv-файла и разделителя.
    // Принимает непосредственно имена файлов.
    public void convert(String csvFileName, char csvSeparator) throws IOException {
        File csvFile = new File(csvFileName);

        String htmlFileName = commons.getHtmlExtensionFileName(csvFileName);
        File htmlFile = new File(htmlFileName);

        if (converter(csvFile, htmlFile, csvSeparator)) {
            setLogs(csvFileName, htmlFileName);
        }
    }

    // Конвертирование непосредственно путем передачи имени csv-файла, html-файла и разделителя.
    // Принимает непосредственно имена файлов.
    public void convert(String csvFileName, String htmlFileName, char csvSeparator) throws IOException {
        File csvFile = new File(csvFileName);
        File htmlFile = new File(htmlFileName);

        if (converter(csvFile, htmlFile, csvSeparator)) {
            setLogs(csvFileName, htmlFileName);
        }
    }

    // Конвертер csv в html.
    // Принимает на вход файлы и разделитель.
    // Создает html-файл и возвращает true в случае успеха.
    private boolean converter(File csvFile, File htmlFile, char csvSeparator) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile));
             PrintWriter writer = new PrintWriter(htmlFile)) {
            writer.print("""
                    <!doctype html>
                    <html lang="ru">
                    <head>
                        <link rel="alternate" hreflang="en" href="http://en.example.com/" />
                        <meta charset="utf-8">
                        <title>CSV to HTML</title>
                        <style>
                            body {margin: 0px; padding: 30px}
                            table, td, tr {border: 1px solid black; border-collapse: collapse}
                        </style>
                    </head>
                    <body>
                        <table>
                    """);

            boolean isComplicatedTextMode = false;
            boolean isNewTableRow = true;
            int quotesAmount = 0;
            String line;

            while (((line = reader.readLine()) != null)) {
                if (isNewTableRow) {
                    writer.println("       <tr>");
                    writer.print("         <td>");
                    isNewTableRow = false; // обнуляем режим новой строки;
                }

                for (int i = 0; i < line.length(); i++) {
                    char symbol = line.charAt(i);

                    if (symbol == '"') {
                        quotesAmount++;
                    }

                    // Если число кавычек нечетное или нулевое - мы находимся в режиме
                    // "непростого текста" (с запятыми внутри, переносами и кавычками).
                    // Четное число кавычек говорит о конце режима "непростого" текста.

                    // Встречаем разделитель:
                    // - если встречаем разделитель в режиме "непростой текст" - значит это просто запятая;
                    // - если встречаем разделитель в режиме "непростой текст закончен" - значит правда разделитель;
                    // - если встречаем конец строки в режиме "непростой текст" - значит это перенос.
                    if (symbol == csvSeparator && quotesAmount % 2 != 0 && !isComplicatedTextMode) {
                        isComplicatedTextMode = true;
                    } else if (symbol == csvSeparator && quotesAmount % 2 == 0 && isComplicatedTextMode) {
                        isComplicatedTextMode = false;
                    }

                    // Первая кавычка в ячейке - входим в режим "непростого текста".
                    if (symbol == '"' && quotesAmount == 1) {
                        isComplicatedTextMode = true;
                        continue;
                        // Закрывающая кавычка - выход из режима "непростого текста", сигнал к концу ячейки.
                    } else if (symbol == '"' && isComplicatedTextMode) {
                        isComplicatedTextMode = false;
                        continue;
                        // Это просто кавычки в тексте, продолжаем в режиме "непростого текста".
                    } else if (symbol == '"' && line.charAt(i - 1) == '"') {
                        isComplicatedTextMode = true;
                    }

                    // Печать символа в файл.
                    if (symbol == '"' && isComplicatedTextMode) {
                        writer.print(symbol);
                    } else if (symbol == csvSeparator && !isComplicatedTextMode) {
                        writer.println("</td>");
                        writer.print("         <td>");

                        quotesAmount = 0; // в конце ячейки нужно обнулить число кавычек;
                    } else {
                        writer.print(replaceSpecialSymbols(symbol));
                    }
                }

                // Действия после окончания считанной линии исходного файла.
                if (isComplicatedTextMode) {
                    writer.print("<br />"); // режим "непростого текста" - будет перенос;
                } else {
                    writer.println("</td>");
                    writer.println("       </tr>");

                    quotesAmount = 0; // в конце линии файла нужно обнулить число кавычек;
                    isNewTableRow = true;
                }
            }

            writer.print("""
                        </table>
                    </body>
                    </html>
                    """);
        }

        return true;
    }

    private static String replaceSpecialSymbols(char symbol) {
        if (symbol == '&') {
            return "&amp;";
        } else if (symbol == '<') {
            return "&lt;";
        } else if (symbol == '>') {
            return "&gt;";
        }

        return String.valueOf(symbol);
    }

    public ArrayList<String> getLogsList() {
        return logsList;
    }

    private void setLogs(String csvFileName, String htmlFileName) {
        logsList.add("Файл: " + csvFileName + " успешно обработан.");
        logsList.add("Результат: " + htmlFileName + ".");
    }
}