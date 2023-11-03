package model;

import java.util.*;

public class MinesKeeperField {
    private final MineCell[][] mineField;
    private char gameStatus; // P - play, L - loose, W - win.
    private int minesAmount;
    private int openedCells = 0;

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

    public int getMinesAmount() {
        return minesAmount;
    }

    public void setMinesAmount(int minesAmount) {
        this.minesAmount = minesAmount;
    }

    public int getOpenedCells() {
        return openedCells;
    }

    public void setOpenedCells(int openedCells) {
        this.openedCells = openedCells;
    }

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
        int minesAmount = getMinesAmount();
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
        int openedCellsAmount = getOpenedCells();

        // Если мина - на выход!
        if (mineField[row][column].isMine) {
            // Открыть все мины.
            openAllMines();
            setGameStatus('F');
        }

        // Проверяем клетки вокруг выбранной клетки. Создаем очередь.
        ArrayList<MineCell> queue = new ArrayList<>();
        // Помещаем в очередь выбранную клетку.
        queue.add(new MineCell(row, column));

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
                setOpenedCells(openedCellsAmount);

                if (getOpenedCells() == mineField[0].length * mineField.length - 5) {
                    setGameStatus('W');
                }

                return;
            }

            // Проверяем восемь клеток вокруг.
            for (int i = currentCell.row - 1; i < currentCell.row + 2; i++) {
                if (i < 0 || i == mineField.length) {
                    continue;
                }

                for (int j = currentCell.column - 1; j < currentCell.column + 2; j++) {
                    if (j < 0 || j == mineField[0].length) {
                        continue;
                    }

                    // TODO: ? В Лямбды можно передавать только переменные, которые не меняются внутри кода.
                    int finalI = i;
                    int finalJ = j;

                    // Проверяем есть ли перебираемая клетка в очереди на переборку?
                    // Чтобы второй раз ее в очередь не добавлять.
                    // Если клетка с такими же полями есть в очереди, то
                    // размер отфильтрованного списка list будет равен нулю.
                    // TODO: ? В идеале сделать проверку на нахождение такой же клетки
                    //  в самой очереди, но у меня не получилось.
                    List<MineCell> list = new ArrayList<>(queue.stream()
                            .filter(e -> e.getRow() == finalI && e.getColumn() == finalJ)
                            .toList());

                    // Если: не мина и клетка не обработана ранее, не текущая (в центре девятая) и нет в очереди.
                    //if (!mineField[i][j].isMine && !mineField[i][j].isChecked && (i != currentCell.row || j != currentCell.column) && list.size() == 0) {
                    if (!mineField[i][j].isMine
                            && (i != currentCell.row || j != currentCell.column)
                            && list.size() == 0
                            && !mineField[i][j].isChecked) {
                        // Если пустая, добавляем в очередь и открываем иначе просто открываем.
                        if (mineField[i][j].minesAroundAmount == 0) {
                            queue.add(new MineCell(i, j));
                        }

                        // Если ещ не открыта, открываем.
                        if (!mineField[i][j].status.equals("O")) {
                            // TODO: ? Если не мина. Мину не надо открывать.
                            mineField[i][j].status = "O";

                            openedCellsAmount++;
                        }
                    }

                    // Очищаем список для новой итерации.
                    list.clear();
                }
            }

            // Помечаем клетку как проверенную, открываем. И удаляем из очереди.
            mineField[currentCell.row][currentCell.column].isChecked = true;

            // Если текущая клетка не открыта еще - открываем.
            if (!mineField[currentCell.row][currentCell.column].status.equals("O")) {
                mineField[currentCell.row][currentCell.column].status = "O";
                openedCellsAmount++;
            }

            queue.remove(0);

        }

        setOpenedCells(openedCellsAmount);

        if (getOpenedCells() == mineField[0].length * mineField.length - getMinesAmount()) {
            setGameStatus('W');
        }
    }
}