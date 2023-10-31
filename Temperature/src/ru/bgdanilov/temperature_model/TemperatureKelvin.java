package ru.bgdanilov.temperature_model;

public record TemperatureKelvin(char key, String name) implements ITemperature {

    @Override
    public double convertToCelsius(double temperature) {
        return temperature - 273.15;
    }

    @Override
    public double covertToThis(double temperature) {
        return temperature + 273.15;
    }
}