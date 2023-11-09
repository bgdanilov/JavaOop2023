package sweeper_controller;

import sweeper_model.SweeperCell;
import sweeper_model.SweeperField;

public class SweeperController {
    private final SweeperField mineField;

    public SweeperController(SweeperField mineField) {
        this.mineField = mineField;
    }

    public void fillingMineField() {
        //  Установка/переустановка количества открытых клеток и флагов для новой игры.
        mineField.setOpenedCellsAmount(0);
        mineField.setFlagsAmount(mineField.getMinesAmount());

        mineField.generateCells();
        mineField.generateMines();
        mineField.setCellAdjacentMinesAmount();
    }

    public void processingUserActions(int action, int row, int column) {
        // Справедливо только для закрытых клеток.
        if (mineField.getCell(row, column).getStatus() != 'O') {
            if (action == 1) {
                mineField.openCells(row, column);

            } else if (action == 2) {
                mineField.setCellFlag(row, column);
            }
        }

        mineField.checkWin();
    }

    public SweeperCell[][] getMineField() {
        return mineField.getMineField();
    }

    public char getGameStatus() {
        return mineField.getGameStatus();
    }

    public void setGameStatus(char gameStatus) {
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