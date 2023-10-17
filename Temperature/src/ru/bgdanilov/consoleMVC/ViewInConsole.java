package ru.bgdanilov.consoleMVC;

public class ViewInConsole implements View {
    @Override
    public void showTemperature(MTemperature temperature) {
        System.out.println("Температура: " + temperature.getValue() + temperature.getRange());
    }
}
