package controller;

import model.MinesKeeperField;

public class MinesKeeperController {
    private final MinesKeeperField field;

    public MinesKeeperController(MinesKeeperField field) {
        this.field = field;
    }

    public void addCells() {
        field.makeMineCells();
        field.generateMines();
        field.getMinesAroundAmount();
    }

    public void displayMinesField() {
        field.displayField();
    }

    public void makeAction(int action, int row, int columns) {
        if (action == 1) {
            field.openCell(row, columns);
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
