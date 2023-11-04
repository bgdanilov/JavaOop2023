package model;

import java.util.*;

public class MinesKeeperField {
    private final MineCell[][] mineField;
    private char gameStatus; // P - play, L - loose, W - win.
    private final int minesAmount;
    private int flagsAmount;
    private int openedCellsAmount = 0;

    public MinesKeeperField(int size1, int size2, int minesAmount) {
        mineField = new MineCell[size1][size2];
        this.minesAmount = minesAmount;
        this.flagsAmount = minesAmount;
    }

    public void makeCells() {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[0].length; j++) {
                mineField[i][j] = new MineCell(i, j);
            }
        }
    }

    public void generateMines() {
        int minesAmount = this.minesAmount;
        Random random = new Random();

        for (int i = 0; i < minesAmount; i++) {
            int randomRow = random.nextInt(mineField.length);
            int randomColumn = random.nextInt(mineField[0].length);

            if (mineField[randomRow][randomColumn].isMine) {
                i--;
                continue;
            }

            mineField[randomRow][randomColumn].isMine = true;
        }
    }

    public void setMinesAroundAmount() {
        int minesAmountAround = 0;

        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[0].length; j++) {
                if (!mineField[i][j].isMine) {
                    minesAmountAround = minesAmountAround + calculateMinesAround(i, j);
                }

                mineField[i][j].minesAroundAmount = minesAmountAround;
                minesAmountAround = 0;
            }
        }
    }

    public int calculateMinesAround(int row, int column) {
        int count = 0;

        for (int i = row - 1; i < row + 2; i++) {
            if (i < 0 || i == mineField.length) {
                continue;
            }
            for (int j = column - 1; j < column + 2; j++) {
                if (j < 0 || j == mineField[0].length) {
                    continue;
                }

                if (mineField[i][j].isMine) count++;
            }
        }

        return count;
    }

    public void openAllMines() {
        for (MineCell[] cellsRow : mineField) {
            for (int i = 0; i < mineField[0].length; i++) {
                if (cellsRow[i].isMine) {
                    cellsRow[i].status = 'O';
                }
            }
        }
    }

    public void openCells(int row, int column) {
        // Счетчик открытых клеток.
        int openedCellsAmount = this.openedCellsAmount;

        // Если мина - на выход!
        if (mineField[row][column].isMine) {
            // Открыть все мины.
            openAllMines();
            gameStatus = 'L'; // Loose.

            return;
        }

        // Проверяем клетки вокруг выбранной клетки. Создаем очередь.
        ArrayList<MineCell> queue = new ArrayList<>();
        // Помещаем в очередь выбранную клетку.
        queue.add(new MineCell(row, column));

        int cellsWithoutMinesAmount = mineField[0].length * mineField.length - minesAmount;

        // Пока в очереди есть ожидающие проверки клетки.
        while (queue.size() != 0) {
            // Берем выбранную из очереди - всегда первая.
            MineCell currentCell = queue.get(0);

            // Если попали в цифру, отличную от нуля,
            // открываем, отмечаем как проверенную и выходим.
            if (mineField[currentCell.row][currentCell.column].minesAroundAmount != 0) {
                mineField[currentCell.row][currentCell.column].status = 'O';

                openedCellsAmount++;
                this.openedCellsAmount = openedCellsAmount;

                // Проверка на выигрыш.
                if (openedCellsAmount == cellsWithoutMinesAmount) {
                    gameStatus = 'W';
                }

                return;
            }

            // Если не открыта,
            // Открываем клетку и забываем про нее. Вычеркиваем из очереди.
            if (mineField[currentCell.row][currentCell.column].status != 'O') {
                mineField[currentCell.row][currentCell.column].status = 'O';
                openedCellsAmount++;
            }

            queue.remove(0);

            // Проверяем восемь клеток вокруг.
            for (int i = currentCell.row - 1; i < currentCell.row + 2; i++) {
                if (i < 0 || i == mineField.length) {
                    continue;
                }

                for (int j = currentCell.column - 1; j < currentCell.column + 2; j++) {
                    if (j < 0 || j == mineField[0].length) {
                        continue;
                    }

                    // Если мина или уже открыта, пропускаем.
                    if (mineField[i][j].status == 'O' || mineField[i][j].isMine || mineField[i][j].status == 'F') {
                        continue;
                    }

                    // Если пустая, добавляем в очередь и открываем иначе просто открываем.
                    if (mineField[i][j].minesAroundAmount == 0) {
                        queue.add(new MineCell(i, j));
                    }

                    mineField[i][j].status = 'O';
                    openedCellsAmount++;
                }
            }
        }

        this.openedCellsAmount = openedCellsAmount;

        // Проверка на выигрыш.
        if (openedCellsAmount == cellsWithoutMinesAmount) {
            setGameStatus('W');
        }
    }

    public char getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(char gameStatus) {
        this.gameStatus = gameStatus;
    }

    public MineCell[][] getMineField() {
        return mineField;
    }

    public MineCell getCell(int row, int column) {
        return mineField[row][column];
    }

    public void setCellFlag(int row, int column) {
        if (mineField[row][column].status == 'F') {
            mineField[row][column].status = 'C';
            flagsAmount = flagsAmount + 1;
        } else {
            mineField[row][column].status = 'F';
            flagsAmount = flagsAmount - 1;
        }
    }

    public int getFlagsAmount() {
        return flagsAmount;
    }

    public void setFlagsAmount(int flagsAmount) {
        this.flagsAmount = flagsAmount;
    }

    /* Пока не нужно.

    public int getMinesAmount() {
        return minesAmount;
    }
    public void setMinesAmount(int minesAmount) {
        this.minesAmount = minesAmount;
    }

    public int getOpenedCellsAmount() {
        return openedCellsAmount;
    }

    public void setOpenedCellsAmount(int openedCellsAmount) {
        this.openedCellsAmount = openedCellsAmount;
    }
*/
}