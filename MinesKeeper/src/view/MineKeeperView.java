package view;

import controller.MinesKeeperController;
import model.MineCell;

import java.util.Objects;
import java.util.Scanner;

public class MineKeeperView {
    private final MinesKeeperController controller;

    public MineKeeperView(MinesKeeperController controller) {
        this.controller = controller;
    }

    public void execute() {
        // Действие пользователя без пользователя - создание поля с минами.
        controller.addCells();
        // Получаем созданное минное поле.
        MineCell[][] mineField = controller.getField();
        // Включаем режим игры;
        controller.setGameStatus('P');
        // Отображаем первоначальное поле.
        displayMineField(mineField);

        Scanner scanner = new Scanner(System.in);

        while (controller.gameStatus() == 'P') {
            System.out.print("Введите row: ");
            int row = scanner.nextInt();

            System.out.print("Введите column: ");
            int column = scanner.nextInt();

            System.out.println("Введите действие (1 - открыть, 2 - пометить): ");
            int action = scanner.nextInt();

            controller.makeAction(action, row, column);
            displayMineField(mineField);
        }

        if (controller.gameStatus() == 'F') {
            System.out.println("Вы проиграли!");
        } else if (controller.gameStatus() == 'W') {
            System.out.println("Вы выиграли!");
        }
    }


    public void displayMineField(MineCell[][] mineField) {
        for (MineCell[] cellsRow : mineField) {
            for (int i = 0; i < mineField[0].length; i++) {
                String msg = " ";

                if (Objects.equals(cellsRow[i].getStatus(), "O")) {
                    if (cellsRow[i].isMine()) {
                        msg = "X";
                    } else {
                        int minesAmount = cellsRow[i].getMinesAroundAmount();

                        if (minesAmount == 0) {
                            msg = ".";
                        } else {
                            msg = String.valueOf(minesAmount);
                        }
                    }
                } else if (Objects.equals(cellsRow[i].getStatus(), "F")) {
                    msg = "F";
                }

                // Открыть все мины.
                if (cellsRow[i].isMine()) msg = "X";
                System.out.print(msg + " | ");
            }
            System.out.println();
        }
    }
}


/* 1. В идеале только то, что отображается и только.
 * 2. Мы создаем неизменный объект-массив клеток.
 * Где клетки тоже объекты. Этот массив клеток передаем в контроллер.
 * С помощью контроллера, во Вьюшку получаем этот массив.
 * Далее, в нем меняют состояние клетки, и мы их поля каждый раз выводим новые
 * при помощи функции displayMineField().
 *  Т.е. привязка у нас идет через конструктор.
 *
 *
 */