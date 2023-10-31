package ru.bgdanilov.temperature_model;

import java.util.List;

public record TemperatureModel(List<ITemperature> temperatureScales) implements ITemperatureModel {

    @Override
    public ITemperature chooseScaleObject(char temperatureKey) {
        return temperatureScales.stream()
                .filter(scale -> scale.key() == temperatureKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public ITemperature chooseScaleObject(String temperatureName) {
        return temperatureScales.stream()
                .filter(scale -> scale.name().equals(temperatureName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ITemperature> getTemperatureScales() {
        return temperatureScales;
    }

    @Override
    public boolean checkTemperatureScaleKey(char temperatureScaleKey) {
        return getTemperatureScales().stream()
                .map(ITemperature::key)
                .noneMatch(key -> key == temperatureScaleKey);
    }

    @Override
    public double convertTemperature(double temperature, char inputTemperatureScaleKey, char outputTemperatureScaleKey) {
        ITemperature inputScaleObject = chooseScaleObject(inputTemperatureScaleKey);
        ITemperature outputScaleObject = chooseScaleObject(outputTemperatureScaleKey);

        return outputScaleObject.covertToThis(inputScaleObject.convertToCelsius(temperature));
    }

    @Override
    public double convertTemperature(double temperature, String inputTemperatureScaleName, String outputTemperatureScaleName) {
        ITemperature inputScaleObject = chooseScaleObject(inputTemperatureScaleName);
        ITemperature outputScaleObject = chooseScaleObject(outputTemperatureScaleName);

        return outputScaleObject.covertToThis(inputScaleObject.convertToCelsius(temperature));
    }

    @Override
    public char getTemperatureScaleKey(String temperatureScaleName) {
        return chooseScaleObject(temperatureScaleName).key();
    }
}

// Тезисы:
// Основная часть программы.
// Тут обычно делают соединение с БД, проверки, основные вычисления.