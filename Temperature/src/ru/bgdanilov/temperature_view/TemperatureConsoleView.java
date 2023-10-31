package ru.bgdanilov.temperature_view;

import ru.bgdanilov.temperature_controller.ITemperatureController;
import ru.bgdanilov.temperature_model.ITemperature;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TemperatureConsoleView {
    private final ITemperatureController controller;

    public TemperatureConsoleView(ITemperatureController controller) {
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

        String temperatureScalesKeysLine = controller.getTemperatureScales().stream()
                .map(ITemperature::key)
                .map(Object::toString)
                .collect(Collectors.joining(", ", "(", ")"));

        String inputMessage = "Введите исходную шкалу " + temperatureScalesKeysLine + ":";

        System.out.println(inputMessage);
        char inputTemperatureScaleKey = scanner.next().charAt(0);

        String errorMessage = "Вы должны ввести " + temperatureScalesKeysLine + ".";

        if (controller.checkTemperatureScaleKey(inputTemperatureScaleKey)) {
            throw new NumberFormatException(errorMessage);
        }

        // Ввод результирующей шкалы измерения.
        System.out.println("Введите результирующую шкалу " + temperatureScalesKeysLine + ":");
        char outputTemperatureScaleKey = scanner.next().charAt(0);

        if (controller.checkTemperatureScaleKey(outputTemperatureScaleKey)) {
            throw new NumberFormatException(errorMessage);
        }

        double outputTemperature = controller.convertTemperature(inputTemperature, inputTemperatureScaleKey, outputTemperatureScaleKey);

        String inputTemperatureMessage = "Исходная температура: "
                + getRoundedTemperatureLine(inputTemperature) + " " + inputTemperatureScaleKey;
        String outputTemperatureMessage = "Выходная температура: "
                + getRoundedTemperatureLine(outputTemperature) + " " + outputTemperatureScaleKey;

        System.out.println(inputTemperatureMessage);
        System.out.println(outputTemperatureMessage);
    }

    public static String getRoundedTemperatureLine(double temperature) {
        DecimalFormat temperatureFormat = new DecimalFormat("0.00E00");

        return temperature < 10000
                ? String.valueOf((double) Math.round(temperature * 100) / 100)
                : temperatureFormat.format(temperature);
    }
}

// Тезисы:
// Это то, что можно увидеть и только.
// У Представления есть свойство-поле, привязка к определенному контроллеру.
// Нельзя создавать экземпляры класса друг в друге
// Класс модели во Представлении. Контроллер внутри модели.
// !!!! Представление должно быть подписано на Модель, на ее изменения.
// Или, мой случай. Модель выдает результат Контроллеру, а Контроллер что-то вызывает у Представления.
// 1. Тут подобие Кнопки Представления вызывает метод контроллера.
// 2. Контроллер вызывает метод Модели.
// 3. Модель производит вычисления и отдает результат Контроллеру.
// 4. Контроллер вызывает System.out.println у Представления и отображает результат.
//
//
// Будем писать просто scale без температуры - итак понятно что тут все про температуру.
// Куда засунуть список объектов шкал?