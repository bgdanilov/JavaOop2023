package view;

// import controller.IController;

import model.Celsius;
import model.Fahrenheit;
import model.ITemperature;
import model.Kelvin;

import java.util.Scanner;

public class ConsoleView {
    /*
     В данной редакции контроллер не нужен получается.
     Да и Модель тоже. Модель - это сами классы с названиями температур.

        private final IController controller;

        public ConsoleView(IController controller) {
            this.controller = controller;
        }
    */

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите исходную температуру: ");

        if (!scanner.hasNextDouble()) {
            throw new NumberFormatException("Вы должны ввести число.");
        }

        double inputTemperature = scanner.nextDouble();

        System.out.println("Введите шкалу измерения (C, K, F):");
        char inputTemperatureRange = scanner.next().charAt(0);

        if (inputTemperatureRange != 'C' && inputTemperatureRange != 'K' && inputTemperatureRange != 'F') {
            throw new NumberFormatException("Вы должны ввести C, K или F.");
        }

        System.out.println("Введите во что конвертировать (C, K, F):");
        char outputTemperatureRange = scanner.next().charAt(0);

        if (outputTemperatureRange != 'C' && outputTemperatureRange != 'K' && outputTemperatureRange != 'F') {
            throw new NumberFormatException("Вы должны ввести C, K или F.");
        }

        // Создаем объекты входной и выходной температуры.
        ITemperature inputTemperatureObject = createTemperatureObject(inputTemperatureRange);
        ITemperature outputTemperatureObject = createTemperatureObject(outputTemperatureRange);

        // 1. Все делаем через градусы Цельсия.
        // 2. Объект входной температуры (например K) возвращает ее в Цельсиях.
        // 3. Объект выходной температуры (например F) получает температуру в Цельсиях.
        // 4. И конвертирует ее в свой диапазон (F).
        // 5. Это и есть выходная температура.
        double outputTemperature = outputTemperatureObject
                .covertToThis(inputTemperatureObject.convertToCelsius(inputTemperature));

        String inputTemperatureMessage = "Исходная температура: " + inputTemperature;
        String outputTemperatureMessage = "Выходная температура: " + outputTemperature;

        System.out.println(inputTemperatureMessage);
        System.out.println(outputTemperatureMessage);
    }

    public static ITemperature createTemperatureObject(char temperatureRange) {
        ITemperature temperatureObject;

        if (temperatureRange == 'K') {
            temperatureObject = new Kelvin();
        } else if (temperatureRange == 'F') {
            temperatureObject = new Fahrenheit();
        } else {
            temperatureObject = new Celsius();
        }

        return temperatureObject;
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