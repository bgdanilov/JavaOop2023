package model;

public class Kelvin implements ITemperature {
    @Override
    public double convertToCelsius(double temperature) {
        return temperature - 273.15;
    }

    @Override
    public double covertToThis(double temperature) {
        return temperature + 273.15;
    }
}
