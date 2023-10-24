package model;

public class MineCell {
    // Protected - доступны в пределах пакета. Ячейка доступна в field.
    protected final int row;
    protected final int column;
    protected String status = "C"; // O - opened, C -closed, F - flag
    protected int minesAroundAmount;
    protected boolean isMine;
    protected boolean isChecked;

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

    public String getStatus() {
        return status;
    }

    public boolean isMine() {
        return isMine;
    }

    public int getMinesAroundAmount() {
        return minesAroundAmount;
    }
}

/* 1. Ну ладно. Начнем. Это модель - то, что меняет состояние и хранит его.
 *  Это клетка.
 *  2. У клетки могут быть свои методы. Что она может делать?
 *  - взорваться - закончить игру.
 *  - показать число мин рядом (вернее - выдать это число). Показывает View.
 *  - открыть другие клетки рядом.
 *
 */