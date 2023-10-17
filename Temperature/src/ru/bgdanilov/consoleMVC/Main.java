package ru.bgdanilov.consoleMVC;

public class Main {
    public static void main(String[] args) {
        // Создаем контроллер и выполняем его метод.
        Controller controller = new Controller();
        controller.execute();

    }
}

class Controller {
    // Создаем Модель??
    MModelLayer modelLayer = new MDBLayer();
    // Создаем View.
    View view = new ViewInConsole();
    void execute() {
        MTemperature temperature = modelLayer.getMTemperature();
        view.showTemperature(temperature);
    }
}

/*
1. Модель - это то, откуда мы берем данные.
2. Интерфейс MModelLayer - выгребает все из БД (в нашем случае из Модели)
3. Контроллер выгребает данные из модели и передает ее во View.
*/
