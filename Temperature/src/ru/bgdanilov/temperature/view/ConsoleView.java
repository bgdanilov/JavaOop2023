package ru.bgdanilov.temperature.view;

import ru.bgdanilov.temperature.controller.Controller;
import ru.bgdanilov.temperature.model.Scale;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleView {
    private final Controller controller;

    public ConsoleView(Controller controller) {
        this.controller = controller;
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите исходную температуру:");

        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.println("Ошибка. Вы должны ввести число.");
        }

        double inputTemperature = scanner.nextDouble();

        List<Scale> temperatureScales = controller.getTemperatureScales();

        // Строка обозначений допустимых шкал температуры. Для вывода сообщений.
        String temperatureScalesKeysLine = temperatureScales.stream()
                .map(Scale::key)
                .map(Object::toString)
                .collect(Collectors.joining(", ", "(", ")"));

        // Ввод исходной шкалы измерения.
        System.out.println("Введите исходную шкалу " + temperatureScalesKeysLine + ":");
        String inputTemperatureScaleKey = scanner.next();

        while (inputTemperatureScaleKey.length() > 1
                || getTemperatureScale(temperatureScales, inputTemperatureScaleKey.charAt(0)) == null) {
            System.out.println("Вы должны ввести один символ " + temperatureScalesKeysLine + ".");
            inputTemperatureScaleKey = scanner.next();
        }

        Scale inputScale = getTemperatureScale(temperatureScales, inputTemperatureScaleKey.charAt(0));

        /* * Старое оставим пока.
        String inputMessage = "Введите исходную шкалу " + temperatureScalesKeysLine + ":";

        System.out.println(inputMessage);
        char inputTemperatureScaleKey = scanner.next().charAt(0);

        Scale inputScale = getTemperatureScale(temperatureScales, inputTemperatureScaleKey);

        String errorMessage = "Вы должны ввести " + temperatureScalesKeysLine + ".";

        if (inputScale == null) {
            throw new NumberFormatException(errorMessage);
        }
        * */

        // Ввод результирующей шкалы измерения.
        System.out.println("Введите результирующую шкалу " + temperatureScalesKeysLine + ":");
        String outputTemperatureScaleKey = scanner.next();

        while (outputTemperatureScaleKey.length() > 1
                || getTemperatureScale(temperatureScales, outputTemperatureScaleKey.charAt(0)) == null) {
            System.out.println("Вы должны ввести один символ " + temperatureScalesKeysLine + ".");
            outputTemperatureScaleKey = scanner.next();
        }

        Scale outputScale = getTemperatureScale(temperatureScales, outputTemperatureScaleKey.charAt(0));

        // Расчет и округление выходной температуры.
        double outputTemperature = controller.convertTemperature(inputTemperature, inputScale, outputScale);

        String inputTemperatureMessage = "Исходная температура: "
                + getRoundedTemperatureLine(inputTemperature) + " " + inputTemperatureScaleKey;
        String outputTemperatureMessage = "Выходная температура: "
                + getRoundedTemperatureLine(outputTemperature) + " " + outputTemperatureScaleKey;

        System.out.println(inputTemperatureMessage);
        System.out.println(outputTemperatureMessage);
    }

    private static Scale getTemperatureScale(List<Scale> temperatureScales, char temperatureKey) {
        return temperatureScales.stream()
                .filter(scale -> scale.key() == temperatureKey)
                .findFirst()
                .orElse(null);
    }

    private static String getRoundedTemperatureLine(double temperature) {
        DecimalFormat temperatureFormat = new DecimalFormat("0.00E00");

        return temperature < 10000
                ? String.valueOf((double) Math.round(temperature * 100) / 100)
                : temperatureFormat.format(temperature);
    }
}