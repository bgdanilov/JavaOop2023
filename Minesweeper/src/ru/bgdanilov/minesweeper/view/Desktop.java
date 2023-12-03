package ru.bgdanilov.minesweeper.view;

import ru.bgdanilov.minesweeper.controller.Controller;

import javax.swing.*;

public class Desktop {
    private final Controller controller;

    public Desktop(Controller controller) {
        // Конструируем фрейм один раз, чтобы "Новая игра" повторно этого не делала.
        this.controller = controller;
    }

    public void execute() {
        SwingUtilities.invokeLater(() -> {
            DesktopGame game = new DesktopGame(controller);
            game.startGame();
        });
    }
}

