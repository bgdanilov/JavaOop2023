package sweeper_view;

import sweeper_controller.SweeperController;


import javax.swing.*;

public class SweeperDesktopViewMain {
    private final SweeperController controller;

    public SweeperDesktopViewMain(SweeperController controller) {
        // Конструируем фрейм один раз, чтобы "Новая игра" повторно этого не делала.
        this.controller = controller;
    }

    public void execute() {
        SwingUtilities.invokeLater(() -> {
            SweeperDesktopGame game = new SweeperDesktopGame(controller);
            game.startGame();
        });
    }
}

