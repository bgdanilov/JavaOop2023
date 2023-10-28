package model;

public interface IModel {
    public void convertTemperature(char outputRange);
    public char getRange();
    public void setRange(char range);
    public double getTemperature();
    public void setTemperature(double temperature);
}
