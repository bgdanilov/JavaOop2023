package ru.bgdanilov.temperature_main;

import ru.bgdanilov.temperature.controller.Controller;
import ru.bgdanilov.temperature.model.*;
import ru.bgdanilov.temperature.view.DesktopView;

import java.util.Arrays;
import java.util.List;

public class DesktopMain {
    public static void main(String[] args) {

        List<IScale> temperatureScales = Arrays.asList(
                new ScaleCelsius('C', "Цельсий"),
                new ScaleKelvin('K', "Кельвин"),
                new ScaleFahrenheit('F', "Фаренгейт")
        );

        Model model = new Model(temperatureScales);
        Controller controller = new Controller(model);
        DesktopView userView = new DesktopView(controller);

        userView.execute();
    }
}