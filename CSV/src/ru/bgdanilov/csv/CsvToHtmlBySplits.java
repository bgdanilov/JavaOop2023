// Версия 1. Оставлю данный довольно хитрый алгоритм для истории.
// Можно не проверять.
// К тому же тут ест ошибки, например, не учтен вариант нахождения разделителя в тексте
// - будет сделано ненужное разбитие.
package ru.bgdanilov.csv;

import java.io.*;
import java.util.ArrayList;
import java.io.File;

public class CsvToHtmlBySplits {
    private static final String UTF8_BOM = "\uFEFF";

    private final String csvFilename;
    private final char csvSeparator;
    private final String lineSeparator;

    public CsvToHtmlBySplits(String sourceFilename, Character cvsSeparator, String lineSeparator) {
        this.csvFilename = sourceFilename;
        this.csvSeparator = cvsSeparator;
        this.lineSeparator = lineSeparator;
    }

    public void parseFile() {
        try {
            File csvFile = new File("CSV/src/files/" + csvFilename);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));

            File htmlFile = new File("CSV/src/files/html.html");
            deleteHtmlFileExists(htmlFile);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(htmlFile, true));

            writeLine("""
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
                    """, bufferedWriter);

            String line;
            ArrayList<String> tableRow = new ArrayList<>();
            StringBuilder cellBuffer = new StringBuilder();
            boolean isTransferredTextCell = false;

            while ((line = bufferedReader.readLine()) != null) {
                String[] lineCells = line.split(String.valueOf(csvSeparator), -1);
                lineCells[0] = removeUTF8BOM(lineCells[0]);

                // О чем нам могут рассказать двойные кавычки?
                // Положению кавычек доверия нет - все слишком разнообразно. Остается их количество.
                // Замечено, что текст ячейки с переносом всегда имеет Нечетное количество кавычек.
                // (одна слева и кратно двум внутри текста).
                // Соответственно текст ячейки без переноса всегда имеет Четное количество кавычек или не имеет их вообще.
                // Сигналом, что начались переносы текста является смена маркера isTransferredTextCell с false на true.
                // Сигнал, что переноса текста больше не будет - смена маркера isTransferredTextCell с true на false.

                // Если обнаружена ячейка с переносным текстом - запоминаем это.
                for (String cell : lineCells) {
                    if (isQuotesAmountOdd(cell) && !isTransferredTextCell) {
                        isTransferredTextCell = true;
                    } else if (isQuotesAmountOdd(cell) && isTransferredTextCell) {
                        isTransferredTextCell = false;
                    }

                    if (isTransferredTextCell) { // первый раз встречаем текст с переносом.
                        cell = replaceSpecialSymbols(cell);
                        cellBuffer.append(cell).append("<br />");
                    } else {
                        // Переносы в данном тексте закончились (буфер не пуст). Это последний перенесенный текст.
                        if (!cellBuffer.isEmpty()) {
                            cell = replaceSpecialSymbols(cell);
                            cellBuffer.append(cell);

                            cell = cellBuffer.toString();
                            cell = removeSideQuotes(cell);
                            cell = cell.replace("\"\"", "\""); // Меням двойные кавычки на одинарные.

                            tableRow.add(cell);
                            cellBuffer = new StringBuilder();
                            // А если буфер так и пуст - значит не было переносов вообще.
                        } else {
                            cell = replaceSpecialSymbols(cell);
                            cell = removeSideQuotes(cell);
                            cell = cell.replace("\"\"", "\""); // Меням двойные кавычки на одинарные.

                            tableRow.add(cell);
                        }
                    }
                } // end for

                // Это признак, что строка будущей таблицы закончилась.
                // Либо все ячейки были без переноса, что так и не привело к isTransferredTextCell true.
                // Либо всё закончилось последним текстом массива lineCells, который (текст),
                // в любом случае (с переносом-без переноса) закончится сменой маркера isTransferredTextCell на false.
                if (!isTransferredTextCell) {
                    //System.out.println(tableRow);
                    writeLine("<tr>", bufferedWriter);

                    for (String tableDetail : tableRow) {
                        writeLine("<td>" + tableDetail + "</td>", bufferedWriter);
                    }

                    tableRow.clear();
                    writeLine("</tr>", bufferedWriter);
                }
            }  // end while

            writeLine("""
                    </table>
                    </body>
                    </html>""", bufferedWriter);

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeLine(String line, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(line + lineSeparator);
    }

    private static void deleteHtmlFileExists(File htmlFile) {
        if (htmlFile.exists()) {
            if (htmlFile.delete()) {
                System.out.println("Старый файл HTML-файл удален.");
            }
        } else {
            System.out.println("Создан файл " + htmlFile);
        }
    }

    private static String replaceSpecialSymbols(String string) {
        if (string.contains("<")) {
            string = string.replace("<", "&lt;");
        }

        if (string.contains(">")) {
            string = string.replace(">", "&qt;");
        }

        if (string.contains("&")) {
            string = string.replace("&", "&apm;");
        }

        return string;
    }

    private static String removeUTF8BOM(String string) {
        if (string.startsWith(UTF8_BOM)) {
            string = string.substring(1);
        }

        return string;
    }

    public String removeSideQuotes(String string) {
        if (string.length() != 0 && string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"') {
            return string.substring(1, string.length() - 1);
        }

        return string;
    }

    private static boolean isQuotesAmountOdd(String string) {
        int quotesAmount = 0;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '"') {
                quotesAmount++;
            }
        }

        return quotesAmount % 2 != 0;
    }
}