package model;

public class Model implements IModel {
    protected double temperature;
    protected char range;

    public void convertTemperature(char outputRange) {
        if (range == outputRange) {
            return;
        }

        switch (range) {
            case 'C':
                switch (outputRange) {
                    case 'K' -> {
                        setTemperature(temperature + 273.15);
                        setRange(outputRange);
                        return;
                    }
                    case 'F' -> {
                        setTemperature(temperature * 1.8 + 32.0);
                        setRange(outputRange);
                        return;
                    }
                }
            case 'K':
                switch (outputRange) {
                    case 'C' -> {
                        setTemperature(temperature - 273.15);
                        setRange(outputRange);
                        return;
                    }
                    case 'F' -> {
                        setTemperature(1.8 * (temperature - 273.15) + 32.0);
                        setRange(outputRange);
                        return;
                    }
                }
            case 'F':
                switch (outputRange) {
                    case 'C' -> {
                        setTemperature(5.0 * (temperature - 32.0) / 9.0);
                        setRange(outputRange);
                        return;
                    }
                    case 'K' -> {
                        setTemperature(273.15 + 5.0 * (temperature - 32.0) / 9.0);
                        setRange(outputRange);
                        return;
                    }
                }
            default:
                throw new IllegalArgumentException("Ошибка.");
        }
    }

    public char getRange() {
        return range;
    }

    public void setRange(char range) {
        this.range = range;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = (double) Math.round(temperature * 100) / 100;
    }
}

// Тезисы:
// Основная часть программы.
// Тут обычно делают соединение с БД, проверки, основные вычисления.