package ru.bgdanilov.minesweeper.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public enum DifficultyLevel {
    EASY("Легкий", 10, 10, 1, new String[4]),
    MEDIUM("Средний", 15, 20, 30, new String[4]),
    HARD("Тяжелый", 20, 30, 60, new String[4]);

    private final String label;
    private final int rowsAmount;
    private final int columnsAmount;
    private final int minesAmount;
    private String[] recordEntry;

    DifficultyLevel(String label, int rowsAmount, int columnsAmount, int minesAmount, String[] recordEntry) {
        this.label = label;
        this.rowsAmount = rowsAmount;
        this.columnsAmount = columnsAmount;
        this.minesAmount = minesAmount;
        this.recordEntry = recordEntry;
    }

    public String getLabel() {
        return label;
    }

    public int getRowsAmount() {
        return rowsAmount;
    }

    public int getColumnsAmount() {
        return columnsAmount;
    }

    public int getMinesAmount() {
        return minesAmount;
    }

    public String[] getRecordEntry() {
        return recordEntry;
    }

    public void setRecordEntry(String[] recordEntry) {
        this.recordEntry = recordEntry;
    }

    public static void readFileToEnum() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Minesweeper/src/files/recordsEntries.txt"));
            String recordEntry;
            Scanner scanner = new Scanner(reader);

            for (DifficultyLevel difficultyLevel : DifficultyLevel.values()) {
                recordEntry = scanner.nextLine();
                difficultyLevel.setRecordEntry(recordEntry.split(";"));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace(); // Что это?
        }
    }

    public static void writeEnumToFile() {
        try {
            FileWriter writer = new FileWriter("Minesweeper/src/files/recordsEntries.txt");

            for (DifficultyLevel difficultyLevel : DifficultyLevel.values()) {
                writer.write(String.join(";", difficultyLevel.getRecordEntry()));
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Возникла ошибка во время записи, проверьте данные.");
        }
    }

    @Override
    public String toString() {
        return getLabel();
    }
}