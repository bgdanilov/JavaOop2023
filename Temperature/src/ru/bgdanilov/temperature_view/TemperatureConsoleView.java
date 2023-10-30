package ru.bgdanilov.temperature_view;

import ru.bgdanilov.temperature_controller.ITemperatureController;
import ru.bgdanilov.temperature_model.ITemperature;

import java.text.DecimalFormat;
import java.util.List;
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

        // Ввод исходной шкалы измерения.
        List<Character> temperatureScalesKeys = controller.getTemperatureScales().stream()
                .map(ITemperature::getKey)
                .toList();

        String temperatureScalesKeysLine = temperatureScalesKeys.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "(", ")"));

        String temperatureScalesInputMessage = "Введите исходную шкалу " + temperatureScalesKeysLine + ":";

        System.out.println(temperatureScalesInputMessage);
        char inputTemperatureScale = scanner.next().charAt(0);

        String temperatureScalesErrorMessage = "Вы должны ввести " + temperatureScalesKeysLine + ".";

        if (temperatureScalesKeys.stream().noneMatch(key -> key == inputTemperatureScale)) {
            throw new NumberFormatException(temperatureScalesErrorMessage);
        }

        // Ввод результирующей шкалы измерения.
        System.out.println("Введите результирующую шкалу " + temperatureScalesKeysLine + ":");
        char outputTemperatureScale = scanner.next().charAt(0);

        if (temperatureScalesKeys.stream().noneMatch(key -> key == outputTemperatureScale)) {
            throw new NumberFormatException(temperatureScalesErrorMessage);
        }

        // Выбираем объекты входной и выходной температуры.
        double outputTemperature = controller
                .getOutputTemperature(inputTemperature, inputTemperatureScale, outputTemperatureScale);

        String inputTemperatureMessage = "Исходная температура: "
                + getRoundedTemperatureLine(inputTemperature) + " " + inputTemperatureScale;
        String outputTemperatureMessage = "Выходная температура: "
                + getRoundedTemperatureLine(outputTemperature) + " " + outputTemperatureScale;

        System.out.println(inputTemperatureMessage);
        System.out.println(outputTemperatureMessage);
    }

    public static String getRoundedTemperatureLine(double temperature) {
        DecimalFormat temperatureFormat = new DecimalFormat("0.00E00");

        return temperature < 10000
                ? String.valueOf(temperature)
                : temperatureFormat.format(temperature);
    }
}

// Тезисы:
// Это то, что можно увидеть и только.
// У Вьюшки есть свойство-поле, привязка к определенному контроллеру.
// Нельзя создавать экземпляры класса друг в друге
// Класс модели во Вьюшке. Контроллер внутри модели.
// !!!! Вью должна быть подписана на Модель, на ее изменения.
// Или, мой случай. Модель выдает результат Контроллеру, а Контроллер что-то вызывает у Вьюшки.
// 1. Тут подобие Кнопки Вьюшки вызывает метод контроллера.
// 2. Контроллер вызывает метод Модели.
// 3. Модель производит вычисления и отдает результат Контроллеру.
// 4. Контроллер вызывает System.out.println у Вьюшки и отображает результат.
//
//
// Будем писать просто scale без температуры - итак понятно что тут все про температуру.
// Куда засунуть список объектов шкал?