package controller;

public interface IController {

    void setTemperatureData(double temperature, char range);
    String getTemperatureData();
    void convertTemperature(char outputRange);
}
