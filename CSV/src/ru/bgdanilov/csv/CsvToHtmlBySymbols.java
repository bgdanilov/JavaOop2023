// Версия 2. Посимвольное считывание без split() и т.п.
package ru.bgdanilov.csv;

import java.io.*;

public class CsvToHtmlBySymbols {
    private final char csvSeparator;

    public CsvToHtmlBySymbols(char csvSeparator) {
        this.csvSeparator = csvSeparator;
    }

    public void parseCsvToHtml(String csvFileName, String htmlFileName) throws IOException {
        File csvFile = new File(csvFileName);
        File htmlFile = new File(htmlFileName);

        deleteHtmlFileExists(htmlFile);

        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
                PrintWriter printWriter = new PrintWriter(htmlFile)
        ) {
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

            printWriter.println("       <tr>");
            printWriter.print("         <td>");

            boolean isComplicatedTextMode = false;
            boolean isNewTableRow = false;
            int quotesAmount = 0;
            String line;

            while (((line = bufferedReader.readLine()) != null)) {
                if (isNewTableRow) {
                    printWriter.println("       <tr>");
                    printWriter.print("         <td>");
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

    private static void deleteHtmlFileExists(File htmlFileName) {
        if (htmlFileName.exists()) {
            if (htmlFileName.delete()) {
                System.out.println("Старый файл HTML-файл удален.");
            }
        } else {
            System.out.println("Создан файл " + htmlFileName);
        }
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