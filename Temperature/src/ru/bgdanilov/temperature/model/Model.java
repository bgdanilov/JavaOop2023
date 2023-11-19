package ru.bgdanilov.temperature.model;

import java.util.List;

public record Model(List<IScale> temperatureScales) implements IModel {

    @Override
    public List<IScale> getTemperatureScales() {
        return temperatureScales;
    }


    @Override
    public double convertTemperature(double temperature, IScale inputTemperatureRange, IScale outputTemperatureRange) {
        return outputTemperatureRange.covertFromCelsius(inputTemperatureRange.convertToCelsius(temperature));
    }
}