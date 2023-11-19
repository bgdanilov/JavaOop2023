package ru.bgdanilov.temperature.model;

import java.util.List;

public record Model(List<ScaleInterface> temperatureScales) implements ModelInterface {

    @Override
    public List<ScaleInterface> getTemperatureScales() {
        return temperatureScales;
    }


    @Override
    public double convertTemperature(double temperature, ScaleInterface inputTemperatureRange, ScaleInterface outputTemperatureRange) {
        return outputTemperatureRange.covertFromCelsius(inputTemperatureRange.convertToCelsius(temperature));
    }
}