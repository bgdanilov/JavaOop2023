// Версия 3. Правильные пути файлов. Передача путей через args[] и непосредственно в конвертер.
package ru.bgdanilov.csv;

import java.io.*;
import java.util.ArrayList;

public class CsvToHtmlBySymbols {
    private final Settings settings;
    private final ArrayList<String> logsList = new ArrayList<>();

    public CsvToHtmlBySymbols(Settings settings) {
        this.settings = settings;
    }

    // Конвертирование по переданным args[].
    // Использует данные из args[], хранящиеся в settings.
    public void convert() throws IOException {
        String csvFileName = settings.getCsvFileName();
        File csvFile = new File(csvFileName);

        String htmlFileName = settings.getHtmlFileName();
        File htmlFile = new File(htmlFileName);

        char csvSeparator = settings.getSeparator();

        if (fileConverter(csvFile, htmlFile, csvSeparator)) {
            setLogs(csvFileName, htmlFileName);
        }
    }

    // Конвертирование непосредственно путем передачи только имени csv-файла и разделителя.
    // Принимает непосредственно имена файлов.
    public void convert(String csvFileName, char csvSeparator) throws IOException {
        File csvFile = new File(csvFileName);

        String htmlFileName = settings.composeHtmlFileName(csvFileName);
        File htmlFile = new File(settings.composeHtmlFileName(csvFileName));

        if (fileConverter(csvFile, htmlFile, csvSeparator)) {
            setLogs(csvFileName, htmlFileName);
        }
    }

    // Конвертирование непосредственно путем передачи имени csv-файла, html-файла и разделителя.
    // Принимает непосредственно имена файлов.
    public void convert(String csvFileName, String htmlFileName, char csvSeparator) throws IOException {
        File csvFile = new File(csvFileName);
        File htmlFile = new File(htmlFileName);

        if (fileConverter(csvFile, htmlFile, csvSeparator)) {
            setLogs(csvFileName, htmlFileName);
        }
    }

    // Конвертер csv в html.
    // Принимает на вход файлы и разделитель.
    // Создает html-файл и возвращает true в случае успеха.
    private boolean fileConverter(File csvFile, File htmlFile, char csvSeparator) throws IOException {
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

    private void setLogs(String csvFileName, String htmlFileName) {
        logsList.add("Файл: " + csvFileName + " успешно обработан.");
        logsList.add("Результат: " + htmlFileName + ".");
    }

    private static String replaceSpecialSymbols(char symbol) {
        if (symbol == '&') {
            return "&amp;";
        }

        else if (symbol == '<') {
            return "&lt;";
        }

       else if (symbol == '>') {
            return "&gt;";
        }

        return String.valueOf(symbol);
    }

    public void printLogs() {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        for (String item : logsList) {
            sb.append(item).append(lineSeparator);
        }

        sb.setLength(sb.length() - 1);

        System.out.println(sb);
    }
}