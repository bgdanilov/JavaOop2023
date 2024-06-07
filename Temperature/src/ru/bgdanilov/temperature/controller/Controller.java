package ru.bgdanilov.temperature.controller;

import ru.bgdanilov.temperature.model.Scale;

import java.util.List;

public interface Controller {
    List<Scale> getTemperatureScales();

    double convertTemperature(double temperature, Scale inputTemperatureRange, Scale outputTemperatureRange);
}