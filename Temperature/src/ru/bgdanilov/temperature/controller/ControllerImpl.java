package ru.bgdanilov.temperature.controller;

import ru.bgdanilov.temperature.model.Model;
import ru.bgdanilov.temperature.model.Scale;

import java.util.List;

public class ControllerImpl implements Controller {
    private final Model model;

    public ControllerImpl(Model model) {
        this.model = model;
    }

    @Override
    public List<Scale> getTemperatureScales() {
        return model.getTemperatureScales();
    }

    @Override
    public double convertTemperature(double temperature, Scale inputTemperatureRange, Scale outputTemperatureRange) {
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