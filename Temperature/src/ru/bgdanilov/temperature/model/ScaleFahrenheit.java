package ru.bgdanilov.temperature.model;

public record ScaleFahrenheit(char key, String name) implements IScale {

    @Override
    public double convertToCelsius(double temperature) {
        return 5.0 * (temperature - 32.0) / 9.0;
    }

    @Override
    public double covertFromCelsius(double temperature) {
        return temperature * 1.8 + 32.0;
    }

    @Override
    public String toString() {
        return name;
    }
}