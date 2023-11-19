package ru.bgdanilov.temperature.controller;

import ru.bgdanilov.temperature.model.IScale;

import java.util.List;

public interface IController {
    List<IScale> getTemperatureScales();

    double convertTemperature(double temperature, IScale inputTemperatureRange, IScale outputTemperatureRange);
}