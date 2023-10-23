package model;

import java.util.*;

public class MinesKeeperField {
    private final MineCell[][] field;

    public MinesKeeperField(int size) {
        field = new MineCell[size][size];
    }

    public void setFlag(int row, int column) {
        field[row][column].status = "F";
    }

    public void makeMineCells() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = new MineCell(i, j);
            }
        }
    }

    public void displayField() {
        for (MineCell[] mineCells : field) {
            for (int j = 0; j < field.length; j++) {
                String msg = " ";

                if (Objects.equals(mineCells[j].status, "O")) {
                    if (mineCells[j].isMine) {
                        msg = "X";
                    } else {
                        int minesAmount = mineCells[j].minesAroundAmount;

                        if (minesAmount == 0) {
                            msg = ".";
                        } else {
                            msg = String.valueOf(minesAmount);
                        }
                    }
                } else if (Objects.equals(mineCells[j].status, "F")) {
                    msg = "F";
                }

                // Открыть все мины.
                if (mineCells[j].isMine) msg = "X";

                System.out.print(msg + " | ");
            }

            System.out.println();
        }
    }

    public void generateMines() {
        int minesAmount = field.length;
        Random rd = new Random();

        for (int i = 0; i < minesAmount; i++) {
            int rdRow = rd.nextInt(field.length);
            int rdColumn = rd.nextInt(field.length);

            if (field[rdRow][rdColumn].isMine) {
                continue;
            }

            field[rdRow][rdColumn].isMine = true;
        }
    }

    public void calculateMinesAround() {
        int minesAmount = 0;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (!field[i][j].isMine) {
                    minesAmount = minesAmount + calculateMinesAround(i, j);
                }

                field[i][j].minesAroundAmount = minesAmount;
                minesAmount = 0;
            }
        }
    }

    public int calculateMinesAround(int row, int column) {
        int count = 0;

        for (int i = row - 1; i < row + 2; i++) {
            if (i < 0 || i == field.length) {
                continue;
            }
            for (int j = column - 1; j < column + 2; j++) {
                if (j < 0 || j == field.length) {
                    continue;
                }

                if (field[i][j].isMine) count++;
            }
        }

        return count;
    }

    public void openCells(int row, int column) {
        // Открываем выбранную клетку.
        field[row][column].status = "O";

        if (field[row][column].isMine) {
            return;
        }

        // Проверяем клетки вокруг выбранной клетки. Создаем очередь.
        ArrayList<MineCell> queue = new ArrayList<>();
        // Помещаем в очередь выбранную клетку.
        queue.add(new MineCell(row, column));

        // Пока в очереди есть ожидающие проверки клетки.
        while (queue.size() != 0) {
            // Берем выбранную очереди из кармана - всегда первая.
            MineCell currentCell = queue.get(0);
            row = currentCell.row;
            column = currentCell.column;

            // Если попали в цифру, отличную от нуля - то вокруг не проверяем.
            if (field[row][column].minesAroundAmount != 0) {
                return;
            }

            for (int i = row - 1; i < row + 2; i++) {
                if (i < 0 || i == field.length) {
                    continue;
                }

                for (int j = column - 1; j < column + 2; j++) {
                    if (j < 0 || j == field.length) {
                        continue;
                    }

                    // Если не мина и клетка не обработана ранее, то проверяем.
                    if (!field[i][j].isMine && !field[i][j].isChecked) {

                        // Если пустая, добавляем в очередь и открываем иначе просто открываем.
                        if (field[i][j].minesAroundAmount == 0) {
                            queue.add(new MineCell(i, j));
                            //stash.add(new MineCell(i, j));
                        }

                        field[i][j].status = "O";
                    }
                }
            }

            // Помечаем клетку как проверенную. И удаляем из очереди.
            field[row][column].isChecked = true;
            queue.remove(0);
        }
    }
}