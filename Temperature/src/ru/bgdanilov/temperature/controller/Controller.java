package ru.bgdanilov.temperature.controller;

import ru.bgdanilov.temperature.model.IModel;
import ru.bgdanilov.temperature.model.IScale;

import java.util.List;

public class Controller implements IController {
    private final IModel model;

    public Controller(IModel model) {
        this.model = model;
    }

    @Override
    public List<IScale> getTemperatureScales() {
        return model.getTemperatureScales();
    }


    @Override
    public double convertTemperature(double temperature, IScale inputTemperatureRange, IScale outputTemperatureRange) {
        return model.convertTemperature(temperature, inputTemperatureRange, outputTemperatureRange);
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