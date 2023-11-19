package ru.bgdanilov.temperature.model;

import java.util.List;

public interface IModel {
    List<IScale> getTemperatureScales();

    double convertTemperature(double temperature, IScale inputTemperatureRange, IScale outputTemperatureRange);
}