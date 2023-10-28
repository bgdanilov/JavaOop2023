package model;

public class Celsius implements ITemperature {
    @Override
    public double convertToCelsius(double temperature) {
        return temperature;
    }

    @Override
    public double covertToThis(double temperature) {
        return temperature;
    }
}
