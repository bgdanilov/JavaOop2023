package ru.bgdanilov.temperature_main;

import ru.bgdanilov.temperature_controller.TemperatureController;
import ru.bgdanilov.temperature_model.*;
import ru.bgdanilov.temperature_view.TemperatureDesktopView;

import java.util.Arrays;
import java.util.List;

public class TemperatureDesktopMain {
    public static void main(String[] args) {

        List<ITemperature> temperatureScales = Arrays.asList(
                new TemperatureCelsius('C', "Цельсий"),
                new TemperatureKelvin('K', "Кельвин"),
                new TemperatureFahrenheit('F', "Фаренгейт")
        );

        TemperatureModel model = new TemperatureModel(temperatureScales);
        TemperatureController temperatureController = new TemperatureController(model);
        TemperatureDesktopView userView = new TemperatureDesktopView(temperatureController);

        userView.execute();
    }
}