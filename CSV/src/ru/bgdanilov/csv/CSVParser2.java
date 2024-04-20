package ru.bgdanilov.csv;

import java.io.*;

// Версия 2. Посимвольное считывание без split() и т.п.
public class CSVParser2 {
    private final char csvSeparator;

    public CSVParser2(char csvSeparator) {
        this.csvSeparator = csvSeparator;
    }

    public void parseCSVtoHTML(String csvFileName, String htmlFileName) throws IOException {
        File csvFile = new File(csvFileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));

        File htmlFile = new File(htmlFileName);
        deleteHtmlFileExists(htmlFile);

        try (PrintWriter printWriter = new PrintWriter(htmlFile)) {
            printWriter.println("""
                    <!doctype html>
                    <html lang="ru">
                                        
                    <head>
                    <link rel="alternate" hreflang="en" href="http://en.example.com/" />
                    <meta charset="utf-8">
                    <title>Заголовок</title>
                    <style>
                    body {margin: 0px; padding: 30px}
                    table, td, tr {border: 1px solid black; border-collapse: collapse}</style>
                    </head>
                                        
                    <body>
                    <table>
                    """);

            printWriter.println("<tr>");
            printWriter.print("<td>");

            boolean isComplicatedTextMode = false;
            boolean isNewLine = false;

            char comma = csvSeparator;
            char quote = '"';
            int quotesAmount = 0;

            String line;

            while (((line = bufferedReader.readLine()) != null)) {
                if (isNewLine) {
                    printWriter.println("<tr>");
                    printWriter.print("<td>");
                }

                for (int i = 0; i < line.length(); i++) {
                    char tdTextSymbol = line.charAt(i);

                    if (tdTextSymbol == quote) {
                        quotesAmount++;
                    }

                    // Если число кавычек нечетное или нулевое - мы находимся в режиме
                    // "непростого" текста (с запятыми внутри, переносами и кавычками).
                    // Четное число кавычек говорит о конце режима "непростого" текста.

                    // Встречаем разделитель:
                    // - если встречаем разделитель и "непростой" текст - значит это просто запятая;
                    // - если встречаем разделитель и "непростой" текст закончен - значит правда разделитель;
                    // - если встречаем конец строки и "непростой" текст - значит это перенос.
                    if (tdTextSymbol == comma && quotesAmount % 2 != 0 && !isComplicatedTextMode) {
                        isComplicatedTextMode = true;
                    } else if (tdTextSymbol == comma && quotesAmount % 2 == 0 && isComplicatedTextMode) {
                        isComplicatedTextMode = false;
                    }

                    // Первая кавычка в ячейке - входим в режим "непростого" текста.
                    if (tdTextSymbol == quote && quotesAmount == 1) {
                        isComplicatedTextMode = true;
                        continue;
                        // Закрывающая кавычка - выход из режима "непростого" текста, сигнал к концу ячейки.
                    } else if (tdTextSymbol == quote && isComplicatedTextMode) {
                        isComplicatedTextMode = false;
                        continue;
                        // Это просто кавычки в тексте, продолжаем в "непростого" текста.
                    } else if (tdTextSymbol == quote && line.charAt(i - 1) == quote) {
                        isComplicatedTextMode = true;
                    }

                    // Печать символа в файл.
                    if (tdTextSymbol == quote && isComplicatedTextMode) {
                        printWriter.print(tdTextSymbol);
                    } else if (tdTextSymbol == comma && !isComplicatedTextMode) {
                        printWriter.println("</td>");
                        printWriter.print("<td>");

                        quotesAmount = 0; // в конце ячейки нужно обнулить число кавычек.
                    } else {
                        printWriter.print(tdTextSymbol);
                    }
                }

                // Действия после окончания считанной строки исходного файла.
                if (isComplicatedTextMode) {
                    printWriter.print("<br />"); // режим "непростого" текста - будет продолжение.
                } else {
                    printWriter.println("</td>");
                    printWriter.println("</tr>");

                    quotesAmount = 0;
                    isNewLine = true;
                }
            }

            printWriter.println("</table>");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
            e.printStackTrace();
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
}