package ru.bgdanilov.temperature_model;

public class TemperatureCelsius implements ITemperature {

    private char key;
    private String name;

    public TemperatureCelsius(char key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public double convertToCelsius(double temperature) {
        return temperature;
    }

    @Override
    public double covertToThis(double temperature) {
        return temperature;
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
