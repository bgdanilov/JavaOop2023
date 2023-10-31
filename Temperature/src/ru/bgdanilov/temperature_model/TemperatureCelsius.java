package ru.bgdanilov.temperature_model;

public record TemperatureCelsius(char key, String name) implements ITemperature {

    @Override
    public double convertToCelsius(double temperature) {
        return temperature;
    }

    @Override
    public double covertToThis(double temperature) {
        return temperature;
    }
}