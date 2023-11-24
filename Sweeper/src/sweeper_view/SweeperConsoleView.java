package sweeper_view;

import sweeper_controller.SweeperController;
import sweeper_model.SweeperCell;
import sweeper_model.SweeperCellStatus;
import sweeper_model.SweeperGameStatus;

import java.util.Scanner;

public class SweeperConsoleView {
    private final SweeperController controller;

    public SweeperConsoleView(SweeperController controller) {
        this.controller = controller;
    }

    public void execute() {
        // Действие пользователя без пользователя - создание поля с минами.
        controller.fillingMineField();
        // Включаем режим первого беспроигрышного клика.
        controller.setGameStatus(SweeperGameStatus.FIRST_CLICK);
        // Отображаем первоначальное поле.
        displayMineField(controller.getMineField());

        Scanner scanner = new Scanner(System.in);

        while (controller.getGameStatus() == SweeperGameStatus.PLAY
                || controller.getGameStatus() == SweeperGameStatus.FIRST_CLICK) {
            System.out.print("Введите row: ");
            int row = scanner.nextInt();

            System.out.print("Введите column: ");
            int column = scanner.nextInt();

            System.out.println("Введите действие (1 - открыть, 2 - пометить): ");
            int action = scanner.nextInt();

            controller.processUserAction(action, row, column);
            displayMineField(controller.getMineField());
        }

        if (controller.getGameStatus() == SweeperGameStatus.LOOSE) {
            System.out.println("Вы проиграли!");
        } else if (controller.getGameStatus() == SweeperGameStatus.WIN) {
            System.out.println("Вы выиграли!");
        }
    }

    public void displayMineField(SweeperCell[][] mineField) {
        System.out.println("Мин / флагов осталось: " + controller.getFlagsAmount() + ".");

        System.out.print(":)| ");

        for (int columnNumber = 0; columnNumber < mineField[0].length; columnNumber++) {
            System.out.print(columnNumber + " | ");
        }

        System.out.println();

        for (int i = 0; i < mineField.length; i++) {
            System.out.print(i + " | ");

            for (int j = 0; j < mineField[0].length; j++) {
                String cellDisplaying = " ";

                if (mineField[i][j].getStatus() == SweeperCellStatus.OPENED) {
                    if (mineField[i][j].isMine()) {
                        cellDisplaying = "X";
                    } else {
                        int minesAmount = mineField[i][j].getAdjacentMinesAmount();

                        if (minesAmount == 0) {
                            cellDisplaying = ".";
                        } else {
                            cellDisplaying = String.valueOf(minesAmount);
                        }
                    }
                } else if (mineField[i][j].getStatus() == SweeperCellStatus.MARKED) {
                    cellDisplaying = "F";
                }

                // Сделать все мины видимыми для отладки.
                // if (mineField[i][j].isMine()) cellDisplaying = "X";

                System.out.print(cellDisplaying + " | ");
            }
            System.out.println();
        }
    }
}