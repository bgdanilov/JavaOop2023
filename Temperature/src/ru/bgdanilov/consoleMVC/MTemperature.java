package ru.bgdanilov.consoleMVC;

public class MTemperature {
    private double value;
    private String range;

    public MTemperature(double value, String range) {
        this.value = value;
        this.range = range;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}