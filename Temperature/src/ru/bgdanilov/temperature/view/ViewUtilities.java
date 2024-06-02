package ru.bgdanilov.temperature.view;

import ru.bgdanilov.temperature.model.Scale;

import java.text.DecimalFormat;
import java.util.List;

public class ViewUtilities {
    public static String getRoundedTemperatureLine(double temperature) {
        DecimalFormat temperatureFormat = new DecimalFormat("0.00E00");

        return temperature < 10000
                ? String.valueOf((double) Math.round(temperature * 100) / 100)
                : temperatureFormat.format(temperature);
    }

    public static Scale getTemperatureScale(List<Scale> temperatureScales, char temperatureKey) {
        return temperatureScales.stream()
                .filter(scale -> scale.key() == temperatureKey)
                .findFirst()
                .orElse(null);
    }
}
