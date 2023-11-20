package ru.bgdanilov.temperature.model;

public interface ScaleInterface {
    double convertToCelsius(double temperature);

    double covertFromCelsius(double temperature);

    char key();

    String toString();
}