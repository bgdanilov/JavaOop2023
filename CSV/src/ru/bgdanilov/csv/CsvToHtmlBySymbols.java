// Версия 2. Посимвольное считывание без split() и т.п.
package ru.bgdanilov.csv;

import java.io.*;

public class CsvToHtmlBySymbols {
    private final Settings settings;

    private String csvFilePath;

    private String htmlFilePath;

    public CsvToHtmlBySymbols(Settings settings) {
        this.settings = settings;
    }

    public CsvToHtmlBySymbols(Settings settings, String csvFilePath, String htmlFilePath) {
        this.settings = settings;
        this.csvFilePath = csvFilePath;
        this.htmlFilePath = htmlFilePath;
    }

    public void convertCsvToHtml(String csvFilePath, String htmlFilePath) {

    }

    public void convertCsvToHtml() throws IOException {
        File csvFile = new File(settings.getUtilityHome() + settings.getCsvFileName());
        File htmlFile = getHtmlFile(settings.composeHtmlFilePath() + settings.composeHtmlFileName());
        char csvSeparator = settings.getUserSeparator();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
             PrintWriter printWriter = new PrintWriter(htmlFile)) {
            printWriter.print("""
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

            while (((line = bufferedReader.readLine()) != null)) {
                if (isNewTableRow) {
                    printWriter.println("       <tr>");
                    printWriter.print("         <td>");
                    isNewTableRow = false; // обнуляем режим новой строки;
                }

                for (int i = 0; i < line.length(); i++) {
                    char tdTextSymbol = line.charAt(i);

                    if (tdTextSymbol == '"') {
                        quotesAmount++;
                    }

                    // Если число кавычек нечетное или нулевое - мы находимся в режиме
                    // "непростого текста" (с запятыми внутри, переносами и кавычками).
                    // Четное число кавычек говорит о конце режима "непростого" текста.

                    // Встречаем разделитель:
                    // - если встречаем разделитель в режиме "непростой текст" - значит это просто запятая;
                    // - если встречаем разделитель в режиме "непростой текст закончен" - значит правда разделитель;
                    // - если встречаем конец строки в режиме "непростой текст" - значит это перенос.
                    if (tdTextSymbol == csvSeparator && quotesAmount % 2 != 0 && !isComplicatedTextMode) {
                        isComplicatedTextMode = true;
                    } else if (tdTextSymbol == csvSeparator && quotesAmount % 2 == 0 && isComplicatedTextMode) {
                        isComplicatedTextMode = false;
                    }

                    // Первая кавычка в ячейке - входим в режим "непростого текста".
                    if (tdTextSymbol == '"' && quotesAmount == 1) {
                        isComplicatedTextMode = true;
                        continue;
                        // Закрывающая кавычка - выход из режима "непростого текста", сигнал к концу ячейки.
                    } else if (tdTextSymbol == '"' && isComplicatedTextMode) {
                        isComplicatedTextMode = false;
                        continue;
                        // Это просто кавычки в тексте, продолжаем в режиме "непростого текста".
                    } else if (tdTextSymbol == '"' && line.charAt(i - 1) == '"') {
                        isComplicatedTextMode = true;
                    }

                    // Печать символа в файл.
                    if (tdTextSymbol == '"' && isComplicatedTextMode) {
                        printWriter.print(tdTextSymbol);
                    } else if (tdTextSymbol == csvSeparator && !isComplicatedTextMode) {
                        printWriter.println("</td>");
                        printWriter.print("         <td>");

                        quotesAmount = 0; // в конце ячейки нужно обнулить число кавычек;
                    } else {
                        printWriter.print(replaceSpecialSymbols(tdTextSymbol));
                    }
                }

                // Действия после окончания считанной линии исходного файла.
                if (isComplicatedTextMode) {
                    printWriter.print("<br />"); // режим "непростого текста" - будет перенос;
                } else {
                    printWriter.println("</td>");
                    printWriter.println("       </tr>");

                    quotesAmount = 0; // в конце линии файла нужно обнулить число кавычек;
                    isNewTableRow = true;
                }
            }

            printWriter.print("""
                        </table>
                    </body>
                    </html>
                    """);
        }
    }

    private File getHtmlFile(String htmlFileName) {
        File htmlFile = new File(htmlFileName);

        if (htmlFile.exists()) {
            if (htmlFile.delete()) {
                System.out.println("Старый HTML-файл: " + htmlFileName + " удален.");
            }
        }

        System.out.println("Новый HTML-файл: " + htmlFileName + " создан.");

        return htmlFile;
    }

    private static String replaceSpecialSymbols(char tdTextSymbol) {
        String string = String.valueOf(tdTextSymbol);

        if (tdTextSymbol == '&') {
            string = "&apm;";
        } else if (tdTextSymbol == '<') {
            string = "&lt;";
        } else if (tdTextSymbol == '>') {
            string = "&qt;";
        }

        return string;
    }
}