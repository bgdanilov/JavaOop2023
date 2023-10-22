package model;

import java.util.Random;

public class MinesKeeperField {
    //private HashMap<String, MineCell> mineField = new HashMap<>();
    private MineCell[][] field;

    public MinesKeeperField(int size) {
        field = new MineCell[size][size];
    }

    public void makeMineCells() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = new MineCell(i, j);
            }
        }
    }

    public void displayField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                //System.out.print(field[i][j].isMine + "," + field[i][j].minesAroundAmount + "| ");
                String x = String.valueOf(field[i][j].minesAroundAmount);
                String y = field[i][j].status;
                if (field[i][j].isMine) {
                    x = "x";
                } else {
                    x = y;
                }
                System.out.print(x + " | ");
            }

            System.out.println();
        }
    }

    public void generateMines() {
        int minesAmount = field.length;
        Random rd = new Random();

        for (int i = 0; i < minesAmount; i++) {
            int rdRow = rd.nextInt(4);
            int rdColumn = rd.nextInt(4);

            if (field[rdRow][rdColumn].isMine) {
                continue;
            }

            field[rdRow][rdColumn].isMine = true;
        }
    }

    //  Считает еще и саму себя, поэтому нужно отнять -1.
    public void getMinesAroundAmount() {
        int minesAmount = 0;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (!field[i][j].isMine) {
                    minesAmount = minesAmount + checkAround(i, j);
                }

                field[i][j].minesAroundAmount = minesAmount;
                minesAmount = 0;
            }
        }
    }

    public int checkAround(int row, int column) {
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

    public void openEmptyCells(int row, int columns) {

    }

    public void checkAround2(int row, int column) {
        if (!field[row][column].isMine) {
            field[row][column].status = "e";
        } else {
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

                if (i == row && j ==column) {
                    continue;
                }

                if (!field[i][j].isMine) {
                    field[i][j].status = "e";
                    //checkAround2(i, j);
                }
            }
        }
    }

    public void checkAround3(int row, int column) {
        if (!field[row][column].isMine) {
            field[row][column].status = "e";
        } else {
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

                if (i == row && j ==column) {
                    continue;
                }

                if (!field[i][j].isMine) {
                    field[i][j].status = "e";
                }
            }
        }
    }
}


