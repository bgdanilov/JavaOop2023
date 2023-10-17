package ru.bgdanilov.consoleMVC;

public class ViewInHTML implements View {
    @Override
    public void showTemperature(MTemperature temperature) {
        System.out.println("<html>Температура: " + temperature.getValue() + temperature.getRange() +"</html>");
    }
}
