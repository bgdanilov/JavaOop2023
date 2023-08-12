package ru.bgdanilov.deminer;

public class MineField {
    private final Cell[][] field;
    private final int rows;
    private final int columns;

    public MineField(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.field = new Cell[this.rows][this.columns];
    }

    public void setField() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                field[i][j] = new Cell(i, j, "0", true, false, CellType.EMPTY);
            }
        }
    }

    public Cell getCell (int i, int j) {
        return field[i][j];
    }

    public void printField() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                // Если клетка скрыта - рисуем * .
                if(field[i][j].getIsHidden()) {
                    // Скрыта и помечера - рисуем f .
                    if(field[i][j].getIsMarked()) {
                        System.out.print("f ");
                    } else {
                        System.out.print("* ");
                    }
                // Остальные случаи - клетка открыта.
                } else {
                    if(field[i][j].type == CellType.EMPTY) {
                        //System.out.print("e ");
                        System.out.print(field[i][j].type.getName() + " ");
                    } else if (field[i][j].type == CellType.MINE) {
                        //System.out.print("m ");
                        System.out.print(field[i][j].type.getName() + " ");
                    } else if (field[i][j].type == CellType.DIGIT) {
                        System.out.print(field[i][j].type.getNumber() + " ");
                    }
                }
            }

            System.out.println();
        }
    }
}