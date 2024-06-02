package ru.bgdanilov.temperature.view;

public interface View {
    void execute();
    String getRoundedTemperatureLine(double temperature);
}
