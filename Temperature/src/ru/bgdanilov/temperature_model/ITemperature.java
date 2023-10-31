package ru.bgdanilov.temperature_model;

public interface ITemperature {
    double convertToCelsius(double temperature);

    double covertToThis(double temperature);

    char key();

    String name();
}