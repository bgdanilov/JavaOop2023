package ru.bgdanilov.temperature.model;

import java.util.List;

public interface Model {
    List<Scale> getTemperatureScales();

    double convertTemperature(double temperature, Scale inputTemperatureScale, Scale outputTemperatureScale);
}