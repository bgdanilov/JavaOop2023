package sweeper_controller;

import sweeper_model.SweeperCell;
import sweeper_model.SweeperCellStatus;
import sweeper_model.SweeperField;
import sweeper_model.SweeperGameStatus;

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
    }

    public void processUserAction(int action, int row, int column) {
        // Справедливо только для закрытых клеток.
        if (mineField.getCell(row, column).getStatus() != SweeperCellStatus.OPENED) {
            if (action == 1) {
                if (mineField.getGameStatus() == SweeperGameStatus.FIRST_CLICK) {
                    mineField.generateMines(row, column);
                    mineField.setCellAdjacentMinesAmount();
                }

                mineField.setGameStatus(SweeperGameStatus.PLAY);
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

    public SweeperGameStatus getGameStatus() {
        return mineField.getGameStatus();
    }

    public void setGameStatus(SweeperGameStatus gameStatus) {
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