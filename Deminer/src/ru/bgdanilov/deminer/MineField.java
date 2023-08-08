package ru.bgdanilov.deminer;

public class MineField {
    private int rowsNumber; // Число строк.
    private int colsNumber; // Число столбцов.

    public MineField(int rows, int cols) {
        this.rowsNumber = rows;
        this.colsNumber = cols;
    }

    public int getColsNumber() {
        return colsNumber;
    }

    public int getRowsNumber() {
        return rowsNumber;
    }

    public void setColsNumber(int colsNumber) {
        this.colsNumber = colsNumber;
    }

    public void setRowsNumber(int rowsNumber) {
        this.rowsNumber = rowsNumber;
    }
}
