package ru.bgdanilov.temperature.model;

import java.util.List;

public record ModelImpl(List<Scale> temperatureScales) implements Model {
    @Override
    public List<Scale> getTemperatureScales() {
        return temperatureScales;
    }

    @Override
    public double convertTemperature(double temperature, Scale inputTemperatureScale, Scale outputTemperatureScale) {
        return outputTemperatureScale.convertFromCelsius(inputTemperatureScale.convertToCelsius(temperature));
    }
}