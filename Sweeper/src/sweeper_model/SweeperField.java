package sweeper_model;

import java.util.*;

public class SweeperField {
    private final SweeperCell[][] MINE_FIELD;
    private final int MINES_AMOUNT;
    private SweeperGameStatus gameStatus;
    private int flagsAmount;
    private int openedCellsAmount;

    public SweeperField(int rows, int columns, int minesAmount) {
        MINE_FIELD = new SweeperCell[rows][columns];
        this.MINES_AMOUNT = minesAmount;
    }

    public void generateCells() {
        for (int i = 0; i < MINE_FIELD.length; i++) {
            for (int j = 0; j < MINE_FIELD[0].length; j++) {
                MINE_FIELD[i][j] = new SweeperCell(i, j);
            }
        }
    }

    public void generateMines(int row, int column) {
        Random random = new Random();

        for (int i = 0; i < MINES_AMOUNT; i++) {
            int randomRow = random.nextInt(MINE_FIELD.length);
            int randomColumn = random.nextInt(MINE_FIELD[0].length);

            if (MINE_FIELD[randomRow][randomColumn].isMine() || (randomRow == row && randomColumn == column)) {
                i--;
                continue;
            }

            MINE_FIELD[randomRow][randomColumn].setMine(true);
        }
    }

    public void setCellAdjacentMinesAmount() {
        int adjacentMinesAmount = 0;

        for (int i = 0; i < MINE_FIELD.length; i++) {
            for (int j = 0; j < MINE_FIELD[0].length; j++) {
                if (!MINE_FIELD[i][j].isMine()) {
                    adjacentMinesAmount += countCellAdjacentMinesAmount(i, j);
                }

                MINE_FIELD[i][j].setAdjacentMinesAmount(adjacentMinesAmount);
                adjacentMinesAmount = 0;
            }
        }
    }

    public int countCellAdjacentMinesAmount(int row, int column) {
        int adjacentMinesAmount = 0;

        for (int i = row - 1; i < row + 2; i++) {
            if (i < 0 || i == MINE_FIELD.length) {
                continue;
            }
            for (int j = column - 1; j < column + 2; j++) {
                if (j < 0 || j == MINE_FIELD[0].length) {
                    continue;
                }

                if (MINE_FIELD[i][j].isMine()) adjacentMinesAmount++;
            }
        }

        return adjacentMinesAmount;
    }

    public void openAllMines() {
        for (SweeperCell[] cellsRow : MINE_FIELD) {
            for (int i = 0; i < MINE_FIELD[0].length; i++) {
                if (cellsRow[i].isMine()) {
                    cellsRow[i].setStatus(SweeperCellStatus.OPENED);
                }
            }
        }
    }

    public void openCells(int row, int column) {
        // Возвращаем флаг обратно при открытии помеченной клетки.
        if (MINE_FIELD[row][column].getStatus() == SweeperCellStatus.MARKED) {
            flagsAmount = flagsAmount + 1;
        }
        // Счетчик открытых клеток.
        int openedCellsAmount = this.openedCellsAmount;

        // Если мина - на выход!
        if (MINE_FIELD[row][column].isMine()) {
            // Открыть все мины.
            openAllMines();
            gameStatus = SweeperGameStatus.LOOSE; // Loose.

            return;
        }

        // Проверяем клетки вокруг выбранной клетки. Создаем очередь.
        ArrayList<SweeperCell> queue = new ArrayList<>();
        // Помещаем в очередь выбранную клетку.
        queue.add(new SweeperCell(row, column));

        // Пока в очереди есть ожидающие проверки клетки.
        while (queue.size() != 0) {
            // Берем выбранную из очереди - всегда первая.
            SweeperCell currentCell = queue.get(0);
            int currentCellRow = currentCell.getRow();
            int currentCellColumn = currentCell.getColumn();

            // Если попали в цифру, отличную от нуля, открываем и выходим.
            if (MINE_FIELD[currentCellRow][currentCellColumn].getAdjacentMinesAmount() != 0) {
                MINE_FIELD[currentCellRow][currentCellColumn].setStatus(SweeperCellStatus.OPENED);

                openedCellsAmount++;
                this.openedCellsAmount = openedCellsAmount;

                return;
            }

            // Если не открыта,
            // Открываем клетку и проверяем соседние клетки. Вычеркиваем из очереди.
            if (MINE_FIELD[currentCellRow][currentCellColumn].getStatus() != SweeperCellStatus.OPENED) {
                MINE_FIELD[currentCellRow][currentCellColumn].setStatus(SweeperCellStatus.OPENED);
                openedCellsAmount++;
            }

            queue.remove(0);

            // Проверяем восемь клеток вокруг.
            for (int i = currentCellRow - 1; i < currentCellRow + 2; i++) {
                if (i < 0 || i == MINE_FIELD.length) {
                    continue;
                }

                for (int j = currentCellColumn - 1; j < currentCellColumn + 2; j++) {
                    if (j < 0 || j == MINE_FIELD[0].length) {
                        continue;
                    }

                    // Если мина или уже открыта, пропускаем.
                    if (MINE_FIELD[i][j].getStatus() == SweeperCellStatus.OPENED
                            || MINE_FIELD[i][j].isMine()
                            || MINE_FIELD[i][j].getStatus() == SweeperCellStatus.MARKED) {
                        continue;
                    }

                    // Если пустая, добавляем в очередь и открываем иначе просто открываем.
                    if (MINE_FIELD[i][j].getAdjacentMinesAmount() == 0) {
                        queue.add(new SweeperCell(i, j));
                    }

                    MINE_FIELD[i][j].setStatus(SweeperCellStatus.OPENED);
                    openedCellsAmount++;
                }
            }
        }

        this.openedCellsAmount = openedCellsAmount;
    }

    public SweeperGameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(SweeperGameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public SweeperCell[][] getMineField() {
        return MINE_FIELD;
    }

    public SweeperCell getCell(int row, int column) {
        return MINE_FIELD[row][column];
    }

    public void setCellFlag(int row, int column) {
        if (MINE_FIELD[row][column].getStatus() == SweeperCellStatus.MARKED) {
            MINE_FIELD[row][column].setStatus(SweeperCellStatus.CLOSED);
            flagsAmount = flagsAmount + 1;
        } else {
            MINE_FIELD[row][column].setStatus(SweeperCellStatus.MARKED);
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

    public void checkWin() {
        // Если число открытых клеток равно общему количеству клеток минус количество мин.
        // И флаги расставлены правильно.
        if (openedCellsAmount == MINE_FIELD[0].length * MINE_FIELD.length - MINES_AMOUNT
                && flagsAmount == 0) {
            setGameStatus(SweeperGameStatus.WIN);
        }
    }

    public int getMinesAmount() {
        return MINES_AMOUNT;
    }

    public void setOpenedCellsAmount(int openedCellsAmount) {
        this.openedCellsAmount = openedCellsAmount;
    }
}