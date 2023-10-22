package view;

import controller.MinesKeeperController;

public class MineKeeperView {
    private final MinesKeeperController controller;

    public MineKeeperView(MinesKeeperController controller) {
        this.controller = controller;
    }
    public void execute() {
        // Действие пользователя без пользователя - создание поля с минами.
        controller.addCells();
        controller.displayMinesField();
    }
}


/* 1. В идеале только то, что отображается и только.
*
*
*
*/