package model;

public class MineCell {
    // Protected - доступны в пределах пакета. Ячейка доступна в field.
    protected final int row;
    protected final int column;
    protected char status = 'C'; // O - opened, C -closed, F - flag
    protected int minesAroundAmount;
    protected boolean isMine;

    public MineCell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public boolean getIsMine() {
        return isMine;
    }

    public int getMinesAroundAmount() {
        return minesAroundAmount;
    }
}