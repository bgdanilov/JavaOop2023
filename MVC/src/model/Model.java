package model;

public class Model {
    private double temperature;
    private char range;

    public double convertTemperature(double temperature, char range) {
        if (this.range == range) {
            return temperature;
        }

        switch (this.range) {
            case 'C':
                switch (range) {
                    case 'K' -> {
                        return temperature + 273.15;
                    }
                    case 'F' -> {
                        return temperature * 1.8 + 32.0;
                    }
                }
            case 'K':
                switch (range) {
                    case 'C' -> {
                        return temperature - 273.15;
                    }
                    case 'F' -> {
                        return 1.8 * (temperature - 273.15) + 32.0;
                    }
                }
            case 'F':
                switch (range) {
                    case 'C' -> {
                        return 5.0 * (temperature - 32.0) / 9.0;
                    }
                    case 'K' -> {
                        return 273.15 + 5.0 * (temperature - 32.0) / 9.0;
                    }
                }
            default:
                throw new IllegalArgumentException("Что-то надо написать.");
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
        this.temperature = temperature;
    }
}

// Основная часть программы.
// Тут обычно делают соединение с БД, проверки, основные вычисления..