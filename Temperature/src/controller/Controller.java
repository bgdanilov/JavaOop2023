package controller;

import model.Model;

import java.text.DecimalFormat;

public class Controller {
    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void setTemperatureData(double temperature, char range) {
        model.setTemperature(temperature);
        model.setRange(range);
    }

    public String getTemperatureData() {
        DecimalFormat temperatureFormat = new DecimalFormat("0.00E00");

        return model.getTemperature() < 10000 ?
                String.valueOf(model.getTemperature()) : temperatureFormat.format(model.getTemperature())
                + " "
                + model.getRange();
    }

    public void convertTemperature(char outputRange) {
        model.convertTemperature(outputRange);
    }
}

// Тезисы:
// Это промежуточный класс, передает данные туда-сюда.
// Контроллер вызывает метод Модели. И что-то преобразует.
// Как курьер: привезти товар, проверить получателя, забрать деньги, вернуть деньги на базу.
// Это связка. Сколько Вьюшек - столько и контроллеров.
// Вьюшка обращается к своему контроллеру.
// Контроллер будет много знать про Вьюшку, но он должен быть маленьким, по минимуму.
// Поэтому эта связка - не критичная, в отличие от если бы была связка сложной модели со Вьюшкой.
// Не допускать перемещения данных из Модели в контроллер. Сообщения об ошибках и прочее.