package ru.bgdanilov.temperature_model;

public interface ITemperature {
    double convertToCelsius(double temperature);

    double covertToThis(double temperature);

    char getKey();
    void setKey(char key);
    String getName();
    void setName(String name);
}