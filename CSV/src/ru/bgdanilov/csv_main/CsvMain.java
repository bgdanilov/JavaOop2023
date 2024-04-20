package ru.bgdanilov.csv_main;

import ru.bgdanilov.csv.CSVParser2;
import java.io.IOException;

public class CsvMain {
    public static void main(String[] args) {
        CSVParser2 csvParser = new CSVParser2(',');

        try {
            csvParser.parseCSVtoHTML("CSV/src/files/csv2.csv", "CSV/src/files/html.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


/**
        String lineSeparator = System.lineSeparator();
        CSVParser1 parser = new CSVParser1("csv4.csv", ',', lineSeparator);

        parser.parseFile();
*/
    }
}