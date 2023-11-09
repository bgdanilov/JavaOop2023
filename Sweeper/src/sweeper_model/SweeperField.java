package sweeper_model;

import java.util.*;

public class SweeperField {
    private final SweeperCell[][] MINE_FIELD;
    private final int MINES_AMOUNT;
    private char gameStatus; // P - play, L - loose, W - win.
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

    public void generateMines() {
        Random random = new Random();

        for (int i = 0; i < MINES_AMOUNT; i++) {
            int randomRow = random.nextInt(MINE_FIELD.length);
            int randomColumn = random.nextInt(MINE_FIELD[0].length);

            if (MINE_FIELD[randomRow][randomColumn].isMine) {
                i--;
                continue;
            }

            MINE_FIELD[randomRow][randomColumn].isMine = true;
        }
    }

    public void setCellAdjacentMinesAmount() {
        int adjacentMinesAmount = 0;

        for (int i = 0; i < MINE_FIELD.length; i++) {
            for (int j = 0; j < MINE_FIELD[0].length; j++) {
                if (!MINE_FIELD[i][j].isMine) {
                    adjacentMinesAmount += countCellAdjacentMinesAmount(i, j);
                }

                MINE_FIELD[i][j].adjacentMinesAmount = adjacentMinesAmount;
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

                if (MINE_FIELD[i][j].isMine) adjacentMinesAmount++;
            }
        }

        return adjacentMinesAmount;
    }

    public void openAllMines() {
        for (SweeperCell[] cellsRow : MINE_FIELD) {
            for (int i = 0; i < MINE_FIELD[0].length; i++) {
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
        if (MINE_FIELD[row][column].isMine) {
            // Открыть все мины.
            openAllMines();
            gameStatus = 'L'; // Loose.

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

            // Если попали в цифру, отличную от нуля, открываем и выходим.
            if (MINE_FIELD[currentCell.row][currentCell.column].adjacentMinesAmount != 0) {
                MINE_FIELD[currentCell.row][currentCell.column].status = 'O';

                openedCellsAmount++;
                this.openedCellsAmount = openedCellsAmount;

                return;
            }

            // Если не открыта,
            // Открываем клетку и проверяем соседние клетки. Вычеркиваем из очереди.
            if (MINE_FIELD[currentCell.row][currentCell.column].status != 'O') {
                MINE_FIELD[currentCell.row][currentCell.column].status = 'O';
                openedCellsAmount++;
            }

            queue.remove(0);

            // Проверяем восемь клеток вокруг.
            for (int i = currentCell.row - 1; i < currentCell.row + 2; i++) {
                if (i < 0 || i == MINE_FIELD.length) {
                    continue;
                }

                for (int j = currentCell.column - 1; j < currentCell.column + 2; j++) {
                    if (j < 0 || j == MINE_FIELD[0].length) {
                        continue;
                    }

                    // Если мина или уже открыта, пропускаем.
                    if (MINE_FIELD[i][j].status == 'O' || MINE_FIELD[i][j].isMine || MINE_FIELD[i][j].status == 'F') {
                        continue;
                    }

                    // Если пустая, добавляем в очередь и открываем иначе просто открываем.
                    if (MINE_FIELD[i][j].adjacentMinesAmount == 0) {
                        queue.add(new SweeperCell(i, j));
                    }

                    MINE_FIELD[i][j].status = 'O';
                    openedCellsAmount++;
                }
            }
        }

        this.openedCellsAmount = openedCellsAmount;
    }

    public char getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(char gameStatus) {
        this.gameStatus = gameStatus;
    }

    public SweeperCell[][] getMineField() {
        return MINE_FIELD;
    }

    public SweeperCell getCell(int row, int column) {
        return MINE_FIELD[row][column];
    }

    public void setCellFlag(int row, int column) {
        if (MINE_FIELD[row][column].status == 'F') {
            MINE_FIELD[row][column].status = 'C';
            flagsAmount = flagsAmount + 1;
        } else {
            MINE_FIELD[row][column].status = 'F';
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
            setGameStatus('W');
        }
    }

    public int getMinesAmount() {
        return MINES_AMOUNT;
    }

    public void setOpenedCellsAmount(int openedCellsAmount) {
        this.openedCellsAmount = openedCellsAmount;
    }
}