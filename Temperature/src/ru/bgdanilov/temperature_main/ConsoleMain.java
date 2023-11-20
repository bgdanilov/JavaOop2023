package ru.bgdanilov.temperature_main;

import ru.bgdanilov.temperature.controller.Controller;
import ru.bgdanilov.temperature.model.*;
import ru.bgdanilov.temperature.view.Console;

import java.util.Arrays;
import java.util.List;

public class ConsoleMain {
    public static void main(String[] args) {
        List<ScaleInterface> temperatureScales = Arrays.asList(
                new ScaleCelsius('C', "Цельсий"),
                new ScaleKelvin('K', "Кельвин"),
                new ScaleFahrenheit('F', "Фаренгейт")
        );

        try {
            Model model = new Model(temperatureScales);
            Controller controller = new Controller(model);
            Console userView = new Console(controller);

            userView.execute();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}