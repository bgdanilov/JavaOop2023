package ru.bgdanilov.temperature_main;

import ru.bgdanilov.temperature.controller.Controller;
import ru.bgdanilov.temperature.model.*;
import ru.bgdanilov.temperature.view.DesktopOneInputField;

import java.util.Arrays;
import java.util.List;

public class DesktopOneInputFieldMain {
    public static void main(String[] args) {

        List<ScaleInterface> temperatureScales = Arrays.asList(
                new ScaleCelsius('C', "Цельсий"),
                new ScaleKelvin('K', "Кельвин"),
                new ScaleFahrenheit('F', "Фаренгейт")
        );

        Model model = new Model(temperatureScales);
        Controller controller = new Controller(model);
        DesktopOneInputField userView = new DesktopOneInputField(controller);

        userView.execute();
    }
}