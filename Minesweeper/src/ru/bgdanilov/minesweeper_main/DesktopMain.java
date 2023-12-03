package ru.bgdanilov.minesweeper_main;

import ru.bgdanilov.minesweeper.controller.Controller;
import ru.bgdanilov.minesweeper.model.DifficultyLevel;
import ru.bgdanilov.minesweeper.model.Field;
import ru.bgdanilov.minesweeper.view.Desktop;


public class DesktopMain {
    public static void main(String[] args) {
        // TODO: После отладки, сделать число мин автоматически равным 10% от количества всех клеток.
        Field mineField = new Field(DifficultyLevel.EASY);
        Controller controller = new Controller(mineField);
        //SweeperDesktopView userView = new SweeperDesktopView(controller);
        Desktop userView = new Desktop(controller);

        userView.execute();
    }
}