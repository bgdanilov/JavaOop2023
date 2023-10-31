package ru.bgdanilov.temperature_controller;

import ru.bgdanilov.temperature_model.ITemperature;

import java.util.List;

public interface ITemperatureController {
    List<ITemperature> getTemperatureScales();

    boolean checkTemperatureScaleKey(char temperatureScaleKey);

/*  Возможно, пригодится, если буду выбирать объект температурной шкалы в Представлении.
    ITemperature chooseTemperatureObject(char inputTemperatureScaleKey);
    ITemperature chooseTemperatureObject(String inputTemperatureScaleName);
*/

    double convertTemperature(double temperature, char inputTemperatureScaleKey, char outputTemperatureScaleKey);

    double convertTemperature(double temperature, String inputTemperatureScaleName, String outputTemperatureScaleName);

    char getTemperatureScaleKey(String temperatureScaleName);
}
