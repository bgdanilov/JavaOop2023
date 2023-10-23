package view;

import controller.MinesKeeperController;

import java.util.Scanner;

public class MineKeeperView {
    private final MinesKeeperController controller;

    public MineKeeperView(MinesKeeperController controller) {
        this.controller = controller;
    }
    public void execute() {
        // Действие пользователя без пользователя - создание поля с минами.
        controller.addCells();
        // Отображаем первоначальное поле.
        controller.displayMinesField();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите row: ");
            int row = scanner.nextInt();

            System.out.print("Введите column: ");
            int column = scanner.nextInt();

            System.out.println("Введите действие (1 - открыть, 2 - пометить): ");
            int action = scanner.nextInt();

            controller.makeAction(action, row, column);
            controller.displayMinesField();
        }
    }
}


/* 1. В идеале только то, что отображается и только.
*
*
*
*/