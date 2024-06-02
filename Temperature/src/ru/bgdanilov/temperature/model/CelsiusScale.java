package ru.bgdanilov.temperature.model;

public record CelsiusScale(char key, String name) implements Scale {
    @Override
    public double convertToCelsius(double temperature) {
        return temperature;
    }

    @Override
    public double covertFromCelsius(double temperature) {
        return temperature;
    }

    @Override
    public String toString() {
        return name;
    }
}