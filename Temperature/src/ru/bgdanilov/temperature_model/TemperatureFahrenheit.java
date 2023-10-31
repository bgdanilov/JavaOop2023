package ru.bgdanilov.temperature_model;

public class TemperatureFahrenheit implements ITemperature {
    private char key;
    private String name;

    public TemperatureFahrenheit(char key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public double convertToCelsius(double temperature) {
        return 5.0 * (temperature - 32.0) / 9.0;
    }

    @Override
    public double covertToThis(double temperature) {
        return temperature * 1.8 + 32.0;
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