package model;

public class MineCell {
    // protected - доступны в пределах пакета. Ячейка доступна в field.
    protected int row;
    protected int column;
    protected String status = "C"; // E - opened, C -closed, F - flag
    protected int minesAroundAmount;
    protected boolean isMine; //

    public MineCell(int row, int column) {
        this.row = row;
        this.column = column;
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