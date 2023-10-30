package model;

public class Fahrenheit implements ITemperature {
    @Override
    public double convertToCelsius(double temperature) {
        return 5.0 * (temperature - 32.0) / 9.0;
    }

    @Override
    public double covertToThis(double temperature) {
        return temperature * 1.8 + 32.0;
    }
}
