package ru.bgdanilov.minesweeper.model;

public class Cell {
    private final int row;
    private final int column;
    private CellStatus status;
    private int adjacentMinesAmount;
    private boolean isMine;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        status = CellStatus.CLOSED;
    }

    public  int getRow() {
        return row;
    }

    public  int getColumn() {
        return column;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }

    public int getAdjacentMinesAmount() {
        return adjacentMinesAmount;
    }

    public void setAdjacentMinesAmount(int adjacentMinesAmount) {
        this.adjacentMinesAmount = adjacentMinesAmount;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }
}