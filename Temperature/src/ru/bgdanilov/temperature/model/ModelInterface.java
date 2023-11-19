package ru.bgdanilov.temperature.model;

import java.util.List;

public interface ModelInterface {
    List<ScaleInterface> getTemperatureScales();

    double convertTemperature(double temperature, ScaleInterface inputTemperatureRange, ScaleInterface outputTemperatureRange);
}