package ru.bgdanilov.deminer;

public class Cell {
    private int rowNumber;
    private int colNumber;
    private String name;

    public Cell(int rowNumber, int colNumber, String name) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColNumber() {
        return colNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public String getName() {
        return name;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}