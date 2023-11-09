package view;

import controller.MinesKeeperController;
import model.MineKeeperCell;

import java.util.Scanner;

public class MineKeeperView {
    private final MinesKeeperController controller;

    public MineKeeperView(MinesKeeperController controller) {
        this.controller = controller;
    }

    public void execute() {
        // Действие пользователя без пользователя - создание поля с минами.
        controller.makeMineField();
        // Включаем режим игры;
        controller.setGameStatus('P');
        // Отображаем первоначальное поле.
        displayMineField(controller.getMineField());

        Scanner scanner = new Scanner(System.in);

        while (controller.getGameStatus() == 'P') {
            System.out.print("Введите row: ");
            int row = scanner.nextInt();

            System.out.print("Введите column: ");
            int column = scanner.nextInt();

            System.out.println("Введите действие (1 - открыть, 2 - пометить): ");
            int action = scanner.nextInt();

            controller.makeAction(action, row, column);
            displayMineField(controller.getMineField());
        }

        if (controller.getGameStatus() == 'L') {
            System.out.println("Вы проиграли!");
        } else if (controller.getGameStatus() == 'W') {
            System.out.println("Вы выиграли!");
        }
    }

    public void displayMineField(MineKeeperCell[][] mineField) {
        System.out.println("Мин / флагов осталось: " + controller.getFlagsAmount() + ".");

        System.out.print(":)| ");

        for (int columnNumber = 0; columnNumber < mineField[0].length; columnNumber++) {
            System.out.print(columnNumber + " | ");
        }

        System.out.println();

        for (int i = 0; i < mineField.length; i++) {
            System.out.print(i + " | ");

            for (int j = 0; j < mineField[0].length; j++) {
                String msg = " ";

                if (mineField[i][j].getStatus() == 'O') {
                    if (mineField[i][j].getIsMine()) {
                        msg = "X";
                    } else {
                        int minesAmount = mineField[i][j].getMinesAroundAmount();

                        if (minesAmount == 0) {
                            msg = ".";
                        } else {
                            msg = String.valueOf(minesAmount);
                        }
                    }
                } else if (mineField[i][j].getStatus() == 'F') {
                    msg = "F";
                }

                // Сделать все мины видимыми для отладки.
                //if (mineField[i][j].getIsMine()) msg = "X";

                System.out.print(msg + " | ");
            }
            System.out.println();
        }
    }
}