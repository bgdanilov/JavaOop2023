package ru.bgdanilov.temperature.view;

import ru.bgdanilov.temperature.controller.Controller;
import ru.bgdanilov.temperature.model.Scale;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleView implements View {
    private final Controller controller;

    public ConsoleView(Controller controller) {
        this.controller = controller;
    }
    @Override
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
                || ViewUtilities.getTemperatureScale(temperatureScales, inputTemperatureScaleKey.charAt(0)) == null) {
            System.out.println("Вы должны ввести один символ " + temperatureScalesKeysLine + ".");
            inputTemperatureScaleKey = scanner.next();
        }

        Scale inputScale = ViewUtilities.getTemperatureScale(temperatureScales, inputTemperatureScaleKey.charAt(0));

        // Ввод результирующей шкалы измерения.
        System.out.println("Введите результирующую шкалу " + temperatureScalesKeysLine + ":");
        String outputTemperatureScaleKey = scanner.next();

        while (outputTemperatureScaleKey.length() > 1
                || ViewUtilities.getTemperatureScale(temperatureScales, outputTemperatureScaleKey.charAt(0)) == null) {
            System.out.println("Вы должны ввести один символ " + temperatureScalesKeysLine + ".");
            outputTemperatureScaleKey = scanner.next();
        }

        Scale outputScale = ViewUtilities.getTemperatureScale(temperatureScales, outputTemperatureScaleKey.charAt(0));

        // Расчет и округление выходной температуры.
        double outputTemperature = controller.convertTemperature(inputTemperature, inputScale, outputScale);

        String inputTemperatureMessage = "Исходная температура: "
                + ViewUtilities.getRoundedTemperatureLine(inputTemperature) + " " + inputTemperatureScaleKey;
        String outputTemperatureMessage = "Выходная температура: "
                + ViewUtilities.getRoundedTemperatureLine(outputTemperature) + " " + outputTemperatureScaleKey;

        System.out.println(inputTemperatureMessage);
        System.out.println(outputTemperatureMessage);
    }
}