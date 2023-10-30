package ru.bgdanilov.temperature_main;

import ru.bgdanilov.temperature_controller.TemperatureController;
import ru.bgdanilov.temperature_model.*;
import ru.bgdanilov.temperature_model.TemperatureModel;
import ru.bgdanilov.temperature_view.TemperatureConsoleView;

import java.util.Arrays;
import java.util.List;

public class TemperatureConsoleMain {
    public static void main(String[] args) {
        List<ITemperature> temperatureScales = Arrays.asList(
                new TemperatureCelsius('C', "Цельсий"),
                new TemperatureKelvin('K', "Кельвин"),
                new TemperatureFahrenheit('F', "Фаренгейт")
        );

        try {
            TemperatureModel model = new TemperatureModel(temperatureScales);
            TemperatureController temperatureController = new TemperatureController(model);
            TemperatureConsoleView userView = new TemperatureConsoleView(temperatureController);

            userView.execute();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}