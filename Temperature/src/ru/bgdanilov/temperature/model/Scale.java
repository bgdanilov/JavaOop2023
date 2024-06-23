package ru.bgdanilov.temperature.model;

public interface Scale {
    double convertToCelsius(double temperature);

    double convertFromCelsius(double temperature);

    char key();

    String toString();
}