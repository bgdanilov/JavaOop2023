package controller;

import model.MineCell;
import model.MinesKeeperField;

import java.util.Random;

public class MinesKeeperController {
    private MinesKeeperField field;

    public MinesKeeperController(MinesKeeperField field) {
        this.field = field;
    }

    public void addCells() {
        field.makeMineCells();
        field.generateMines();
        field.getMinesAroundAmount();
    }

    public void displayMinesField() {
        field.checkAround2(0, 0);
        field.displayField();
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
