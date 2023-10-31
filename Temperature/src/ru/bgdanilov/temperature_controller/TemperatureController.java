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
    public boolean checkTemperatureScaleKey(char temperatureScaleKey) {
        return model.checkTemperatureScaleKey(temperatureScaleKey);
    }

    @Override
    public boolean checkTemperatureScaleName(String temperatureScaleName) {
        return model.checkTemperatureScaleName(temperatureScaleName);
    }

/*  Возможно, пригодится, если буду выбирать объект температурной шкалы в Представлении.
    @Override
    public ITemperature chooseTemperatureObject(char inputTemperatureScaleKey) {
        return model.chooseTemperatureObject(inputTemperatureScaleKey);
    }

    @Override
    public ITemperature chooseTemperatureObject(String inputTemperatureScaleName) {
        return model.chooseTemperatureObject(inputTemperatureScaleName);
    }
*/
    @Override
    public double convertTemperature(double temperature, char inputTemperatureScaleKey, char outputTemperatureScaleKey) {
        return model.convertTemperature(temperature, inputTemperatureScaleKey, outputTemperatureScaleKey);
    }

    @Override
    public double convertTemperature(double temperature, String inputTemperatureScaleName, String outputTemperatureScaleName) {
        return model.convertTemperature(temperature, inputTemperatureScaleName, outputTemperatureScaleName);
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