package ru.bgdanilov.temperature_main;

import ru.bgdanilov.temperature.controller.ControllerImpl;
import ru.bgdanilov.temperature.model.*;
import ru.bgdanilov.temperature.view.DesktopView;

import java.util.Arrays;
import java.util.List;

public class DesktopMain {
    public static void main(String[] args) {
        List<Scale> temperatureScales = Arrays.asList(
                new CelsiusScale('C', "Цельсий"),
                new KelvinScale('K', "Кельвин"),
                new FahrenheitScale('F', "Фаренгейт")
        );

        ModelImpl model = new ModelImpl(temperatureScales);
        ControllerImpl controller = new ControllerImpl(model);
        DesktopView userView = new DesktopView(controller);

        userView.execute();
    }
}