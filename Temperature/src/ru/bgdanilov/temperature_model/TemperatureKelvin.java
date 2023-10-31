package ru.bgdanilov.temperature_model;

public class TemperatureKelvin implements ITemperature {
    private char key;
    private String name;

    public TemperatureKelvin(char key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public double convertToCelsius(double temperature) {
        return temperature - 273.15;
    }

    @Override
    public double covertToThis(double temperature) {
        return temperature + 273.15;
    }

    @Override
    public char getKey() {
        return this.key;
    }

    @Override
    public void setKey(char key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
