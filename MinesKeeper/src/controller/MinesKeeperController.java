package controller;

import model.MineCell;
import model.MinesKeeperField;

public class MinesKeeperController {
    private final MinesKeeperField field;

    public MinesKeeperController(MinesKeeperField field) {
        this.field = field;
    }

    public MineCell[][] getField() {
        return field.getMineField();
    }

    public void addCells() {
        field.makeMineCells();
        field.generateMines();
        field.setMinesAroundAmount();
    }

    public char gameStatus() {
        return field.getGameStatus();
    }

    public void setGameStatus(char gameStatus) {
        field.setGameStatus(gameStatus);
    }

    public void makeAction(int action, int row, int columns) {
        if (action == 1) {
            field.openCells(row, columns);
        } else if (action == 2) {
            field.setFlag(row, columns);
        }
    }
}

/*
 * 1. Что делает Контроллер?
 * - Реагирует и обрабатывает действия пользователя во View/
 * - Сигнализирует об изменениях в состоянии модели и что она что-то выдает.
 * -
 *
 *
 *
 *
 *
 *
 */
