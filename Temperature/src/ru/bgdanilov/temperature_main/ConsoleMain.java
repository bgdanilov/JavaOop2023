package ru.bgdanilov.temperature_main;

import ru.bgdanilov.temperature.controller.ControllerImpl;
import ru.bgdanilov.temperature.model.*;
import ru.bgdanilov.temperature.view.ConsoleView;

import java.util.Arrays;
import java.util.List;

public class ConsoleMain {
    public static void main(String[] args) {
        List<Scale> temperatureScales = Arrays.asList(
                new CelsiusScale('C', "Цельсий"),
                new KelvinScale('K', "Кельвин"),
                new FahrenheitScale('F', "Фаренгейт")
        );

        try {
            ModelImpl model = new ModelImpl(temperatureScales);
            ControllerImpl controller = new ControllerImpl(model);
            ConsoleView userView = new ConsoleView(controller);

            userView.execute();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}