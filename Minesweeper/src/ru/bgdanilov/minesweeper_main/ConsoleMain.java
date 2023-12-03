package ru.bgdanilov.minesweeper_main;

import ru.bgdanilov.minesweeper.controller.Controller;
import ru.bgdanilov.minesweeper.model.Field;
import ru.bgdanilov.minesweeper.view.Console;

public class ConsoleMain {
    public static void main(String[] args) {
        Field field = new Field(10, 10, 10);
        Controller controller = new Controller(field);
        Console userView = new Console(controller);

        userView.execute();
    }
}