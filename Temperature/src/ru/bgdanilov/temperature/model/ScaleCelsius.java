package ru.bgdanilov.temperature.model;

public record ScaleCelsius(char key, String name) implements IScale {

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