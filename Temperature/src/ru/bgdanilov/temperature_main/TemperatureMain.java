package ru.bgdanilov.temperature_main;

import ru.bgdanilov.temperature.controller.Controller;
import ru.bgdanilov.temperature.controller.ControllerImpl;
import ru.bgdanilov.temperature.model.*;
import ru.bgdanilov.temperature.view.ConsoleView;
import ru.bgdanilov.temperature.view.DesktopSingleFieldView;
import ru.bgdanilov.temperature.view.DesktopView;
import ru.bgdanilov.temperature.view.View;

import java.util.Arrays;
import java.util.List;

public class TemperatureMain {
    public static void main(String[] args) {
        List<Scale> temperatureScales = Arrays.asList(
                new CelsiusScale('C', "Цельсий"),
                new KelvinScale('K', "Кельвин"),
                new FahrenheitScale('F', "Фаренгейт")
        );

        // Получается, можно так:
        Model model = new ModelImpl(temperatureScales);
        Controller controller = new ControllerImpl(model);

        View userView = new DesktopView(controller);
        // View userView = new ConsoleView(controller);
        // View userView = new DesktopSingleFieldView(controller);

        userView.execute();

        /*
         Слева интерфейсы, а справа классы, их реализующие,
         приведенные к типу Интерфейса.
         Вместо:
         ModelImpl model = new ModelImpl(temperatureScales);
         ControllerImpl controller = new ControllerImpl(model);
         DesktopView userView = new DesktopView(controller);
        */
    }
}