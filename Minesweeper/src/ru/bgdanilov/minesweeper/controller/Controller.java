package ru.bgdanilov.minesweeper.controller;

import ru.bgdanilov.minesweeper.model.Cell;
import ru.bgdanilov.minesweeper.model.CellStatus;
import ru.bgdanilov.minesweeper.model.Field;
import ru.bgdanilov.minesweeper.model.GameStatus;

public class Controller {
    private Field mineField;

    public Controller(Field mineField) {
        this.mineField = mineField;
    }

    public void newMineField(int rows, int columns, int minesAmount) {
        mineField = new Field(rows, columns, minesAmount);
    }

    public void fillingMineField() {
        //  Установка/переустановка количества открытых клеток и флагов для новой игры.
        mineField.setOpenedCellsAmount(0);
        mineField.setFlagsAmount(mineField.getMinesAmount());
        mineField.generateCells();
    }

    public void processUserAction(int action, int row, int column) {
        // Справедливо только для закрытых клеток.
        if (mineField.getCell(row, column).getStatus() != CellStatus.OPENED) {
            if (action == 1) {
                if (mineField.getGameStatus() == GameStatus.FIRST_CLICK) {
                    mineField.generateMines(row, column);
                    mineField.setCellAdjacentMinesAmount();
                }

                mineField.setGameStatus(GameStatus.PLAY);
                mineField.openCells(row, column);
            } else if (action == 2) {
                mineField.setCellFlag(row, column);
            }
        }

        mineField.setWinGameStatus();
    }

    public Cell[][] getMineField() {
        return mineField.getMineField();
    }

    public GameStatus getGameStatus() {
        return mineField.getGameStatus();
    }

    public void setGameStatus(GameStatus gameStatus) {
        mineField.setGameStatus(gameStatus);
    }

    public int getFlagsAmount() {
        return mineField.getFlagsAmount();
    }

/* Не используется.
    public int getOpenedCellsAmount() {
        return field.getOpenedCellsAmount();
    }

    public void setOpenedCellsAmount(int openedCellsAmount) {
        field.setOpenedCellsAmount(openedCellsAmount);
    }

    public void setFlagsAmount(int flagsAmount) {
        field.setFlagsAmount(flagsAmount);
    }

    public MineKeeperCell getCell(int row, int column) {
        return field.getCell(row, column);
    }
*/
}