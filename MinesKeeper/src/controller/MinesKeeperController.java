package controller;

import model.MineCell;
import model.MinesKeeperField;

public class MinesKeeperController {
    private final MinesKeeperField field;

    public MinesKeeperController(MinesKeeperField field) {
        this.field = field;
    }

    public void makeMineField() {
        field.makeCells();
        field.generateMines();
        field.setMinesAroundAmount();
    }

    public void makeAction(int action, int row, int column) {
        // Справедливо только для закрытых клеток.
        if (field.getCell(row, column).getStatus() != 'O') {
            if (action == 1) {
                field.openCells(row, column);
            } else if (action == 2) {
                field.setCellFlag(row, column);
            }
        }
    }

    public MineCell[][] getMineField() {
        return field.getMineField();
    }

    public char getGameStatus() {
        return field.getGameStatus();
    }

    public void setGameStatus(char gameStatus) {
        field.setGameStatus(gameStatus);
    }

    public int getFlagsAmount() {
        return field.getFlagsAmount();
    }

    public MineCell getCell(int row, int column) {
        return field.getCell(row, column);
    }
}