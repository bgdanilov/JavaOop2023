package ru.bgdanilov.temperature.model;

public interface Scale {
    double convertToCelsius(double temperature);

    double covertFromCelsius(double temperature);

    char key();

    String toString();
}