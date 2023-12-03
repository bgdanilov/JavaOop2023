package ru.bgdanilov.minesweeper.model;

import java.util.*;

public class Field {
    private final Cell[][] mineField;
    private final int minesAmount;
    private GameStatus gameStatus;
    private int flagsAmount;
    private int openedCellsAmount;

    public Field(DifficultyLevel level) {
        int rows = level.getRowsAmount();
        int columns = level.getColumnsAmount();
        minesAmount = level.getMinesAmount();
        mineField = new Cell[rows][columns];
    }

    public Field(int rows, int columns, int minesAmount) {
        mineField = new Cell[rows][columns];
        this.minesAmount = minesAmount;
    }

    public void generateCells() {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[0].length; j++) {
                mineField[i][j] = new Cell(i, j);
            }
        }
    }

    public void generateMines(int row, int column) {
        Random random = new Random();

        for (int i = 0; i < minesAmount; i++) {
            int randomRow = random.nextInt(mineField.length);
            int randomColumn = random.nextInt(mineField[0].length);

            if (mineField[randomRow][randomColumn].isMine() || (randomRow == row && randomColumn == column)) {
                i--;
                continue;
            }

            mineField[randomRow][randomColumn].setMine(true);
        }
    }

    public void setCellAdjacentMinesAmount() {
        int adjacentMinesAmount = 0;

        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[0].length; j++) {
                if (!mineField[i][j].isMine()) {
                    adjacentMinesAmount += countCellAdjacentMinesAmount(i, j);
                }

                mineField[i][j].setAdjacentMinesAmount(adjacentMinesAmount);
                adjacentMinesAmount = 0;
            }
        }
    }

    public int countCellAdjacentMinesAmount(int row, int column) {
        int adjacentMinesAmount = 0;

        for (int i = row - 1; i < row + 2; i++) {
            if (i < 0 || i == mineField.length) {
                continue;
            }
            for (int j = column - 1; j < column + 2; j++) {
                if (j < 0 || j == mineField[0].length) {
                    continue;
                }

                if (mineField[i][j].isMine()) adjacentMinesAmount++;
            }
        }

        return adjacentMinesAmount;
    }

    public void openAllMines() {
        for (Cell[] cellsRow : mineField) {
            for (int i = 0; i < mineField[0].length; i++) {
                if (cellsRow[i].isMine()) {
                    cellsRow[i].setStatus(CellStatus.OPENED);
                }
            }
        }
    }

    public void openCells(int row, int column) {
        // Возвращаем флаг обратно при открытии помеченной клетки.
        if (mineField[row][column].getStatus() == CellStatus.MARKED) {
            flagsAmount = flagsAmount + 1;
        }
        // Счетчик открытых клеток.
        int openedCellsAmount = this.openedCellsAmount;

        // Если мина - на выход!
        if (mineField[row][column].isMine()) {
            // Открыть все мины.
            openAllMines();
            gameStatus = GameStatus.LOOSE; // Loose.

            return;
        }

        // Проверяем клетки вокруг выбранной клетки. Создаем очередь.
        ArrayList<Cell> queue = new ArrayList<>();
        // Помещаем в очередь выбранную клетку.
        queue.add(new Cell(row, column));

        // Пока в очереди есть ожидающие проверки клетки.
        while (queue.size() != 0) {
            // Берем выбранную из очереди - всегда первая.
            Cell currentCell = queue.get(0);
            int currentCellRow = currentCell.getRow();
            int currentCellColumn = currentCell.getColumn();

            // Если попали в цифру, отличную от нуля, открываем и выходим.
            if (mineField[currentCellRow][currentCellColumn].getAdjacentMinesAmount() != 0) {
                mineField[currentCellRow][currentCellColumn].setStatus(CellStatus.OPENED);

                openedCellsAmount++;
                this.openedCellsAmount = openedCellsAmount;

                return;
            }

            // Если не открыта,
            // Открываем клетку и проверяем соседние клетки. Вычеркиваем из очереди.
            if (mineField[currentCellRow][currentCellColumn].getStatus() != CellStatus.OPENED) {
                mineField[currentCellRow][currentCellColumn].setStatus(CellStatus.OPENED);
                openedCellsAmount++;
            }

            queue.remove(0);

            // Проверяем восемь клеток вокруг.
            for (int i = currentCellRow - 1; i < currentCellRow + 2; i++) {
                if (i < 0 || i == mineField.length) {
                    continue;
                }

                for (int j = currentCellColumn - 1; j < currentCellColumn + 2; j++) {
                    if (j < 0 || j == mineField[0].length) {
                        continue;
                    }

                    // Если мина или уже открыта, пропускаем.
                    if (mineField[i][j].getStatus() == CellStatus.OPENED
                            || mineField[i][j].isMine()
                            || mineField[i][j].getStatus() == CellStatus.MARKED) {
                        continue;
                    }

                    // Если пустая, добавляем в очередь и открываем иначе просто открываем.
                    if (mineField[i][j].getAdjacentMinesAmount() == 0) {
                        queue.add(new Cell(i, j));
                    }

                    mineField[i][j].setStatus(CellStatus.OPENED);
                    openedCellsAmount++;
                }
            }
        }

        this.openedCellsAmount = openedCellsAmount;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Cell[][] getMineField() {
        return mineField;
    }

    public Cell getCell(int row, int column) {
        return mineField[row][column];
    }

    public void setCellFlag(int row, int column) {
        if (mineField[row][column].getStatus() == CellStatus.MARKED) {
            mineField[row][column].setStatus(CellStatus.CLOSED);
            flagsAmount = flagsAmount + 1;
        } else {
            mineField[row][column].setStatus(CellStatus.MARKED);
            flagsAmount = flagsAmount - 1;
        }
    }

    public int getFlagsAmount() {
        return flagsAmount;
    }

    /*  Не используется. */
    public void setFlagsAmount(int flagsAmount) {
        this.flagsAmount = flagsAmount;
    }

    public void setWinGameStatus() {
        // Если число открытых клеток равно общему количеству клеток минус количество мин.
        // И флаги расставлены правильно.
        if (openedCellsAmount == mineField[0].length * mineField.length - minesAmount
                && flagsAmount == 0) {
            setGameStatus(GameStatus.WIN);
        }
    }

    public int getMinesAmount() {
        return minesAmount;
    }

    public void setOpenedCellsAmount(int openedCellsAmount) {
        this.openedCellsAmount = openedCellsAmount;
    }
}