package ru.bgdanilov.temperature_model;

import java.util.List;

public record TemperatureModel(List<ITemperature> temperatureScales) implements ITemperatureModel {

    @Override
    public ITemperature chooseTemperatureObject(char temperatureKey) {
        return temperatureScales.stream()
                .filter(a -> a.getKey() == temperatureKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public double convertTemperature(double temperature, char inputTemperatureScale, char outputTemperatureScale) {
        ITemperature inputObj = chooseTemperatureObject(inputTemperatureScale);
        ITemperature outputObj = chooseTemperatureObject(outputTemperatureScale);

        return outputObj.covertToThis(inputObj.convertToCelsius(temperature));
    }

    @Override
    public List<ITemperature> getTemperatureScales() {
        return temperatureScales;
    }
}

// Тезисы:
// Основная часть программы.
// Тут обычно делают соединение с БД, проверки, основные вычисления.