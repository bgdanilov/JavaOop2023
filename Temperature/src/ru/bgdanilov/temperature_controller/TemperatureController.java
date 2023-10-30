package ru.bgdanilov.temperature_controller;

import ru.bgdanilov.temperature_model.ITemperatureModel;
import ru.bgdanilov.temperature_model.ITemperature;

import java.util.List;

public class TemperatureController implements ITemperatureController {
    private final ITemperatureModel model;

    public TemperatureController(ITemperatureModel model) {
        this.model = model;
    }

    @Override
    public List<ITemperature> getTemperatureScales() {
        return model.getTemperatureScales();
    }

    @Override
    public double getOutputTemperature(double temperature, char inputTemperatureScale, char outputTemperatureScale) {
        return model.convertTemperature(temperature, inputTemperatureScale, outputTemperatureScale);
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