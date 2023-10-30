package model;

// Это другая Модель, которую можно подставить вместо прежней.
// Для примера. Никакой нагрузки не несет.

public class Model2 implements IModel {
    @Override
    public void convertTemperature(char outputRange) {
    }

    @Override
    public char getRange() {
        return 0;
    }

    @Override
    public void setRange(char range) {

    }

    @Override
    public double getTemperature() {
        return 0;
    }

    @Override
    public void setTemperature(double temperature) {

    }
}
