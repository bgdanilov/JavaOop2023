package model;

import java.util.*;

public class MinesKeeperField {
    private final MineCell[][] mineField;
    private char gameStatus; // P - play, L - loose, W - win.
    private final int minesAmount;
    private int openedCellsAmount = 0;

    public MinesKeeperField(int size1, int size2, int minesAmount) {
        mineField = new MineCell[size1][size2];
        this.minesAmount = minesAmount;
    }

    public char getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(char gameStatus) {
        this.gameStatus = gameStatus;
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

    public MineCell[][] getMineField() {
        return mineField;
    }

    public void setFlag(int row, int column) {
        mineField[row][column].status = "F";
    }

    public void makeMineCells() {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[0].length; j++) {
                mineField[i][j] = new MineCell(i, j);
            }
        }
    }

    public void generateMines() {
        int minesAmount = this.minesAmount;
        Random rd = new Random();

        for (int i = 0; i < minesAmount; i++) {
            int rdRow = rd.nextInt(mineField.length);
            int rdColumn = rd.nextInt(mineField[0].length);

            if (mineField[rdRow][rdColumn].isMine) {
                continue;
            }

            mineField[rdRow][rdColumn].isMine = true;
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
                    cellsRow[i].status = "O";
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
                mineField[currentCell.row][currentCell.column].isChecked = true;
                mineField[currentCell.row][currentCell.column].status = "O";

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
            if (!mineField[currentCell.row][currentCell.column].status.equals("O")) {
                mineField[currentCell.row][currentCell.column].status = "O";
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
                    if (mineField[i][j].status.equals("O") || mineField[i][i].isMine) {
                        continue;
                    }

                    // Если пустая, добавляем в очередь и открываем иначе просто открываем.
                    if (mineField[i][j].minesAroundAmount == 0) {
                        queue.add(new MineCell(i, j));
                    }

                    mineField[i][j].status = "O";
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
}