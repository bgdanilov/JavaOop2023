package ru.bgdanilov.temperature.model;

public record ScaleKelvin(char key, String name) implements IScale {

    @Override
    public double convertToCelsius(double temperature) {
        return temperature - 273.15;
    }

    @Override
    public double covertFromCelsius(double temperature) {
        return temperature + 273.15;
    }

    @Override
    public String toString() {
        return name;
    }
}