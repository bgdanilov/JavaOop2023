package view;

import controller.Controller;

import java.util.Scanner;

public class ConsoleView {
    private final Controller inputTemperatudeController;
    private final Controller outputTemperatudeController;

    public ConsoleView(Controller controller1, Controller controller2) {
        inputTemperatudeController = controller1;
        outputTemperatudeController = controller2;
    }

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

        if (outputTemperatureRange != 'C' && inputTemperatureRange != 'K' && inputTemperatureRange != 'F') {
            throw new NumberFormatException("Вы должны ввести C, K или F.");
        }

        inputTemperatudeController.setTemperatureData(inputTemperature, inputTemperatureRange);

        double outTemperature = inputTemperatudeController.getOutputTemperature(inputTemperature, outputTemperatureRange);
        outputTemperatudeController.setTemperatureData(outTemperature, outputTemperatureRange);

        System.out.println("Исходная температура: " + inputTemperatudeController.getTemperatureData());
        System.out.println("Выходная температура: " + outputTemperatudeController.getTemperatureData());
    }
}

// Это то, что можно увидеть и только.
// У вью есть свойство-поле, привязка к определенному контроллеру.
// Нельзя создавать экземпляры класса друг в друге
// Класс модели во Вью. Контроллер внутри модели.
// !!!! Вью должна быть подписана на Модель, на ее изменения.
// Или, мой случай. Модель выдает результат Контроллеру, а Контроллер что-то вызывает у Вью.
// 1. Тут типа Кнопка Вью вызывает метод контроллера.
// 2. Контроллер вызывает метод Модели.
// 3. Модель производит вычисления и отдает результат Контроллеру.
// 4. Контроллер вызывает sout у Вью и отображает результат.