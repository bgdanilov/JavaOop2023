package ru.bgdanilov.temperature.controller;

import ru.bgdanilov.temperature.model.ScaleInterface;

import java.util.List;

public interface ControllerInterface {
    List<ScaleInterface> getTemperatureScales();

    double convertTemperature(double temperature, ScaleInterface inputTemperatureRange, ScaleInterface outputTemperatureRange);
}