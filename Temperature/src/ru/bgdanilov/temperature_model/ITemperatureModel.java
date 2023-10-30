package ru.bgdanilov.temperature_model;

import java.util.List;

public interface ITemperatureModel {
    ITemperature chooseTemperatureObject(char temperatureKey);

    double convertTemperature(double temperature, char inputTemperatureScale, char outputTemperatureScale);

    List<ITemperature> getTemperatureScales();
}
