package ru.bgdanilov.temperature.view;

import ru.bgdanilov.temperature.controller.IController;
import ru.bgdanilov.temperature.model.IScale;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleView {
    private final IController controller;

    public ConsoleView(IController controller) {
        this.controller = controller;
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);

        // Ввод исходной температуры.
        System.out.println("Введите исходную температуру: ");

        if (!scanner.hasNextDouble()) {
            throw new NumberFormatException("Вы должны ввести число.");
        }

        double inputTemperature = scanner.nextDouble();

        List<IScale> temperatureScales = controller.getTemperatureScales();

        // Строка обозначений допустимых шкал температуры. Для вывода сообщений.
        String temperatureScalesKeysLine = temperatureScales.stream()
                .map(IScale::key)
                .map(Object::toString)
                .collect(Collectors.joining(", ", "(", ")"));

        // Ввод исходной шкалы измерения.
        String inputMessage = "Введите исходную шкалу " + temperatureScalesKeysLine + ":";

        System.out.println(inputMessage);
        char inputTemperatureScaleKey = scanner.next().charAt(0);

        IScale inputScale = getTemperatureScale(temperatureScales, inputTemperatureScaleKey);

        String errorMessage = "Вы должны ввести " + temperatureScalesKeysLine + ".";

        if (inputScale == null) {
            throw new NumberFormatException(errorMessage);
        }

        // Ввод результирующей шкалы измерения.
        System.out.println("Введите результирующую шкалу " + temperatureScalesKeysLine + ":");
        char outputTemperatureScaleKey = scanner.next().charAt(0);

        IScale outputScale = getTemperatureScale(temperatureScales, outputTemperatureScaleKey);

        if (outputScale == null) {
            throw new NumberFormatException(errorMessage);
        }

        // Расчет и округление выходной температуры.
        double outputTemperature = controller.convertTemperature(inputTemperature, inputScale, outputScale);

        String inputTemperatureMessage = "Исходная температура: "
                + getRoundedTemperatureLine(inputTemperature) + " " + inputTemperatureScaleKey;
        String outputTemperatureMessage = "Выходная температура: "
                + getRoundedTemperatureLine(outputTemperature) + " " + outputTemperatureScaleKey;

        System.out.println(inputTemperatureMessage);
        System.out.println(outputTemperatureMessage);
    }

    private static IScale getTemperatureScale(List<IScale> temperatureScales, char temperatureKey) {
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