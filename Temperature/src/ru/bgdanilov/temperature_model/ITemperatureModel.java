package ru.bgdanilov.temperature_model;

import java.util.List;

public interface ITemperatureModel {
    ITemperature chooseScaleObject(char temperatureKey);

    ITemperature chooseScaleObject(String name);

    List<ITemperature> getTemperatureScales();

    boolean checkTemperatureScaleKey(char temperatureScaleKey);

    double convertTemperature(double temperature, char inputTemperatureScaleKey, char outputTemperatureScaleKey);

    double convertTemperature(double temperature, String inputTemperatureScaleName, String outputTemperatureScaleName);

    char getTemperatureScaleKey(String temperatureScaleName);
}