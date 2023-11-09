package sweeper_view;

import sweeper_controller.Controller;

import java.util.Scanner;

public class ConsoleView {
    private final Controller controller;

    public ConsoleView(Controller controller) {
        this.controller = controller;
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

        if (outputTemperatureRange != 'C' && outputTemperatureRange != 'K' && outputTemperatureRange != 'F') {
            throw new NumberFormatException("Вы должны ввести C, K или F.");
        }

        // Записываем данные исходной температуры в модель.
        controller.setTemperatureData(inputTemperature, inputTemperatureRange);
        // Формируем сообщение с данными исходной температуры.
        String inputTemperatureMessage = "Исходная температура: " + controller.getTemperatureData();

        // Конвертируем температуру, согласно заданию. Изменяем состояние модели.
        controller.convertTemperature(outputTemperatureRange);
        // Формируем сообщение с данными выходной температуры.
        String outputTemperatureMessage = "Выходная температура: " + controller.getTemperatureData();

        System.out.println(inputTemperatureMessage);
        System.out.println(outputTemperatureMessage);
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