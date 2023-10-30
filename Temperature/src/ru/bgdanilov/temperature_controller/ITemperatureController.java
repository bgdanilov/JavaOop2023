package ru.bgdanilov.temperature_controller;

import ru.bgdanilov.temperature_model.ITemperature;

import java.util.List;

public interface ITemperatureController {
    List<ITemperature> getTemperatureScales();

    double getOutputTemperature(double temperature, char inputTemperatureScale, char outputTemperatureScale);
}
